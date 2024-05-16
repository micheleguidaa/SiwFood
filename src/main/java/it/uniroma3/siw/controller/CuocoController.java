package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    private static String UPLOADED_FOLDER = "uploads/cuochi2/";

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

    @PostMapping("/admin/add/cuoco")
    public String newCuoco(@ModelAttribute("cuoco") Cuoco cuoco, 
                           @RequestParam("fileImage") MultipartFile file, 
                           Model model) {
        if (!cuocoService.existsByNomeAndCognome(cuoco.getNome(), cuoco.getCognome())) {
            try {
                // Assicurati che la directory di upload esista
                Path uploadDir = Paths.get(UPLOADED_FOLDER);
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                // Salva il file nel server
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = uploadDir.resolve(fileName);
                Files.write(path, file.getBytes());

                // Imposta l'URL dell'immagine
                cuoco.setUrlImage("/cuochi2/" + fileName);
                
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
    
    @PostMapping("/admin/delete/cuoco/{id}")
    public String deleteCuoco(@PathVariable("id") Long id) {
        cuocoService.deleteById(id);
        return "redirect:/admin/indexCuochi";
    }
}