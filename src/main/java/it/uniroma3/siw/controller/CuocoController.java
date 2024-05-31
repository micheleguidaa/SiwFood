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


    @GetMapping("/admin/cuochi")
    public String indexCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "admin/indexCuochi.html";
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
    
    @PostMapping("/admin/delete/cuoco/{id}")
    public String deleteCuoco(@PathVariable("id") Long id) {
        cuocoService.deleteById(id);
        return "redirect:/admin/indexCuochi";
    }
    

    @GetMapping("/admin/update/cuoco/{id}")
    public String formEditCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", cuocoService.findById(id));
        return "admin/formModifyCuoco.html";
    }

    @PostMapping("/admin/update/cuoco/{id}")
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
