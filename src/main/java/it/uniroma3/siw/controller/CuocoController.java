package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.CredenzialiService;

import java.io.IOException;

@Controller
public class CuocoController {

    @Autowired
    private CuocoService cuocoService;
    
    @Autowired
    private CredenzialiService credenzialiService;

    @GetMapping("/indexCuochi")
    public String indexCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "admin/indexCuochi.html";
    }

    @PostMapping("/registerCuoco")
    public String registerCuoco(@ModelAttribute("cuoco") Cuoco cuoco,
                                BindingResult cuocoBindingResult, 
                                @ModelAttribute("credenziali") Credenziali credenziali,
                                BindingResult credenzialiBindingResult,
                                @RequestParam("fileImage") MultipartFile file,
                                Model model) {
        if (cuocoBindingResult.hasErrors() || credenzialiBindingResult.hasErrors()) {
            model.addAttribute("messaggioErrore", "Questo cuoco esiste già o ci sono errori nei dati inseriti");
            return "formRegisterCuoco.html";
        }
        try {
            cuocoService.registerCuoco(cuoco, credenziali, file);
            credenzialiService.saveCredenziali(credenziali);
            return "redirect:/login";
        } catch (IOException e) {
            model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine");
            return "formRegisterCuoco.html";
        }
    }

    @GetMapping("/cuochi")
    public String showCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "cuochi.html";
    }

    @GetMapping("/cuoco/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", cuocoService.findById(id));
        return "cuoco.html";
    }

    @PostMapping("/delete/cuoco/{id}")
    public String deleteCuoco(@PathVariable("id") Long id) {
        cuocoService.deleteById(id);
        return "redirect:/admin/indexCuochi";
    }

    @GetMapping("/update/cuoco/{id}")
    public String formEditCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", cuocoService.findById(id));
        return "admin/formModifyCuoco.html";
    }

    @PostMapping("/update/cuoco/{id}")
    public String updateCuoco(@PathVariable("id") Long id,
                              @ModelAttribute("cuoco") Cuoco cuoco,
                              @RequestParam("fileImage") MultipartFile file,
                              Model model) {
        try {
            cuocoService.updateCuoco(id, cuoco, file);
            return "redirect:/admin/indexCuochi";
        } catch (IOException e) {
            model.addAttribute("messaggioErrore", "Errore nella gestione dell'immagine");
            return "admin/formModifyCuoco.html";
        }
    }
}
