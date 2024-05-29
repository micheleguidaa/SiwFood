package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private IngredienteService ingredienteService;


    @GetMapping("/ricette")
    public String showRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "ricette.html";
    }
    
    @GetMapping("/admin/indexRicette")
    public String indexRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "admin/indexRicette.html";
    }

    @GetMapping("/ricetta/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", ricettaService.findById(id));
        return "ricetta.html";
    }

    @GetMapping("/leMieRicette")
    public String showLeMieRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "cuoco/indexLeMieRicette.html";
    }

    @PostMapping("/delete/ricetta/{id}")
    public String deleteRicetta(@PathVariable("id") Long id) {
        ricettaService.deleteById(id);
        return "redirect:/leMieRicette";
    }
    
    @PostMapping("/admin/delete/ricetta/{id}")
    public String deleteRicettaByAdmin(@PathVariable("id") Long id) {
        ricettaService.deleteById(id);
        return "redirect:/admin/indexRicette";
    }

    @GetMapping("/addRicetta")
    public String addRicetta(Model model) {
        List<Ingrediente> ingredienti = (List<Ingrediente>) ingredienteService.findAll();
        model.addAttribute("ingredienti", ingredienti);

        if (ingredienti.isEmpty()) {
            Ingrediente ingrediente = new Ingrediente();
            model.addAttribute("ingredienti", List.of(ingrediente));
        }

        return "cuoco/addRicetta.html";
    }

    @PostMapping("/addRicetta")
    public String addRicetta(@ModelAttribute("ricetta") Ricetta ricetta, 
                             BindingResult ricettaBindingResult,
                             @RequestParam("fileImages") MultipartFile[] files, 
                             @RequestParam("cuocoId") Long cuocoId, 
                             @RequestParam("ingredientiIds") List<Long> ingredientiIds,
                             @RequestParam("quantita") List<String> quantitaList,
                             Model model) {
        if (ricettaBindingResult.hasErrors()) {
            List<Ingrediente> ingredienti = (List<Ingrediente>) ingredienteService.findAll();
            model.addAttribute("ingredienti", ingredienti);
            return "cuoco/addRicetta.html";
        }

        try {
            ricettaService.registerRicetta(ricetta, cuocoId, files, ingredientiIds, quantitaList);
            return "redirect:/leMieRicette";
        } catch (IOException e) {
            model.addAttribute("messaggioErrore", "Errore nel caricamento delle immagini");
            return "cuoco/addRicetta.html";
        }
    }
}
