package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.RigaRicetta;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.FileService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class RicettaController {

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private IngredienteService ingredienteService;
    
    @Autowired
    private CuocoService cuocoService;

    @Autowired
    private FileService fileService;

    private static final String UPLOAD_DIR = "uploads/ricette2/";

    @GetMapping("/ricette")
    public String showRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "ricette.html";
    }

    @GetMapping("/ricetta/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.findById(id));
        return "ricetta.html";
    }

    @GetMapping(value = "/admin/indexRicette")
    public String indexRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "admin/indexRicette.html";
    }

    @GetMapping("/le-mie-ricette")
    public String showLeMieRicette(Model model) {
        model.addAttribute("ricette", ricettaService.findAll());
        return "cuoco/indexRicettePersonali.html";
    }

    @PostMapping("/delete/ricetta/{id}")
    public String deleteCuoco(@PathVariable("id") Long id) {
        ricettaService.deleteById(id);
        return "redirect:/le-mie-ricette";
    }

    @GetMapping("/addRicetta")
    public String addRicetta(Model model) {
        List<Ingrediente> ingredienti = (List<Ingrediente>) ingredienteService.findAll();
        model.addAttribute("ingredienti", ingredienti);

        if (ingredienti.isEmpty()) {
            RigaRicetta rigaRicetta = new RigaRicetta();
            rigaRicetta.setIngrediente(new Ingrediente());
            model.addAttribute("ingredienti", List.of(rigaRicetta));
        }

        return "cuoco/addRicetta.html";
    }

    @PostMapping("/addRicetta")
    public String addRicetta(@ModelAttribute("ricetta") Ricetta ricetta, BindingResult ricettaBindingResult,
            @RequestParam("fileImages") MultipartFile[] files, @RequestParam("cuocoId") Long cuocoId, Model model) {
        List<Ingrediente> ingredienti = (List<Ingrediente>) ingredienteService.findAll();
        model.addAttribute("ingredienti", ingredienti);

        if (ricettaBindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredienti);
            return "cuoco/addRicetta.html";
        }

        // Recupera il cuoco corrente e imposta nella ricetta
        Cuoco cuoco = cuocoService.findById(cuocoId);
        ricetta.setCuoco(cuoco);

        // Gestione delle immagini e salvataggio della ricetta
        List<String> urlsImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String imageUrl = fileService.saveFile(file, UPLOAD_DIR);
                    urlsImages.add(imageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("messaggioErrore", "Errore nel caricamento delle immagini");
                    return "cuoco/addRicetta.html";
                }
            }
        }
        ricetta.setUrlsImages(urlsImages);

        ricettaService.save(ricetta);

        return "redirect:/le-mie-ricette";
    }
}
