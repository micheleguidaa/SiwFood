package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CuocoService;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CuocoController {

    @Autowired
    private CuocoService cuocoService;
    
    @GetMapping("/cuochi")
    public String showCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.getAllCuochi());
        return "cuochi";
    }

    @GetMapping("/admin/cuochi")
    public String indexCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.getAllCuochi());
        return "admin/indexCuochi";
    }

    @GetMapping("/cuoco/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", cuocoService.getCuoco(id));
        return "cuoco";
    }
    
    @PostMapping("/admin/delete/cuoco/{id}")
    public String deleteCuoco(@PathVariable("id") Long id) {
        cuocoService.deleteById(id);
        return "redirect:/admin/cuochi";
    }
    
    @GetMapping("/admin/update/cuoco/{id}")
    public String formUpdateCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", cuocoService.getCuoco(id));
        return "admin/formUpdateCuoco";
    }

    @PostMapping("/admin/update/cuoco/{id}")
    public String updateCuoco(@PathVariable("id") Long id,
                              @ModelAttribute("cuoco") Cuoco cuoco,
                              @RequestParam("fileImage") MultipartFile file,
                              Model model) {
        try {
            cuocoService.updateCuoco(id, cuoco, file);
            model.addAttribute("cuochi", cuocoService.getAllCuochi());
            return "admin/indexCuochi";
        } catch (IOException e) {
            model.addAttribute("messaggioErrore", "Errore nella gestione dell'immagine");
            return "admin/formModifyCuoco";
        }
    }
    

}
