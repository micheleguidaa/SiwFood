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
import java.util.logging.Logger;

@Controller
public class CuocoController {
    @Autowired
    private CuocoService cuocoService;

    private static String UPLOADED_FOLDER = "uploads/cuochi2/";
    private static final Logger logger = Logger.getLogger(CuocoController.class.getName());

    @GetMapping(value = "/formNewCuoco")
    public String formNewCuoco(Model model) {
        model.addAttribute("cuoco", new Cuoco());
        return "formNewCuoco.html";
    }

    @GetMapping(value = "/admin/indexCuochi")
    public String indexCuochi(Model model) {
        model.addAttribute("cuochi", cuocoService.findAll());
        return "admin/indexCuochi.html";
    }

    @PostMapping("add/cuoco")
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
                return "redirect:/admin/indexCuochi";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine");
                return "formNewCuoco.html";
            }
        } else {
            model.addAttribute("messaggioErrore", "Questo cuoco esiste già");
            return "formNewCuoco.html";
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
        Cuoco cuoco = cuocoService.findById(id);
        if (cuoco != null) {
            // Elimina il file associato al cuoco
            try {
                Path path = Paths.get(UPLOADED_FOLDER).resolve(Paths.get(cuoco.getUrlImage()).getFileName().toString());
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            cuocoService.deleteById(id);
        }
        return "redirect:/admin/indexCuochi";
    }

    @GetMapping("/admin/edit/cuoco/{id}")
    public String formEditCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id));
        return "admin/formModifyCuoco.html";
    }

    @PostMapping("/admin/update/cuoco/{id}")
    public String updateCuoco(@PathVariable("id") Long id,
                              @ModelAttribute("cuoco") Cuoco cuoco,
                              @RequestParam("fileImage") MultipartFile file,
                              Model model) {
        Cuoco existingCuoco = cuocoService.findById(id);

        // Aggiorna i dettagli del cuoco
        existingCuoco.setNome(cuoco.getNome());
        existingCuoco.setCognome(cuoco.getCognome());
        existingCuoco.setDataDiNascita(cuoco.getDataDiNascita());

        if (!file.isEmpty()) {
            // Elimina il file esistente
            try {
                Path oldPath = Paths.get(UPLOADED_FOLDER).resolve(Paths.get(existingCuoco.getUrlImage()).getFileName().toString());
                if (Files.exists(oldPath)) {
                    Files.delete(oldPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("messaggioErrore", "Errore nella cancellazione dell'immagine esistente");
                return "admin/formModifyCuoco.html";
            }

            // Salva il nuovo file nel server
            try {
                Path uploadDir = Paths.get(UPLOADED_FOLDER);
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = uploadDir.resolve(fileName);
                Files.write(path, file.getBytes());

                // Imposta il nuovo URL dell'immagine
                existingCuoco.setUrlImage("/cuochi2/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine");
                return "admin/formModifyCuoco.html";
            }
        }

        // Salva le modifiche
        cuocoService.save(existingCuoco);
        return "redirect:/admin/indexCuochi";
    }
}