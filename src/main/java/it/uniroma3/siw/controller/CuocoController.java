package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CuocoService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class CuocoController {
    @Autowired
    private CuocoService cuocoService;

    private static String UPLOADED_FOLDER = "src/main/resources/static/images/cuochi";

    @GetMapping(value="/admin/formNewCuoco")
    public String formNewCuoco(Model model) {
        model.addAttribute("cuoco", new Cuoco());
        return "admin/formNewCuoco.html";
    }

    @GetMapping(value="/admin/indexCuochi")
    public String indexCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "admin/indexCuochi.html";
    }

    @PostMapping("/admin/cuoco")
    public String newCuoco(@ModelAttribute("cuoco") Cuoco cuoco, 
                           @RequestParam("fileImage") MultipartFile file, 
                           Model model) {
        if (!cuocoService.existsByNomeAndCognome(cuoco.getNome(), cuoco.getCognome())) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);
                
                // Imposta l'URL dell'immagine
                cuoco.setUrlImage("/images/" + file.getOriginalFilename());
                
                this.cuocoService.save(cuoco); 
                model.addAttribute("cuoco", cuoco);
                return "cuoco.html";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine");
                return "admin/formNewCuoco.html";
            }
        } else {
            model.addAttribute("messaggioErrore", "Questo cuoco esiste già");
            return "admin/formNewCuoco.html";
        }
    }

    @GetMapping("/cuochi")
    public String showCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "cuochi.html"; 
    }
    
    @GetMapping("/cuoco/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "cuoco.html";
    }
    
    @PostMapping("/admin/cuoco/delete/{id}")
    public String deleteCuoco(@PathVariable("id") Long id) {
        cuocoService.deleteById(id);
        return "redirect:/admin/indexCuochi";
    }
    
    
}
