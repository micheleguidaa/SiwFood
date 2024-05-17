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
import it.uniroma3.siw.service.FileService;

import java.io.IOException;

@Controller
public class CuocoController {
    @Autowired
    private CuocoService cuocoService;
    @Autowired
    private CredenzialiService credenzialiService;
    @Autowired
    private FileService fileService;

    private static final String UPLOADED_FOLDER = "uploads/cuochi2/";

    @GetMapping(value = "/registerCuoco")
    public String formNewCuoco(Model model) {
        model.addAttribute("cuoco", new Cuoco());
        model.addAttribute("credenziali", new Credenziali());
        return "formRegisterCuoco.html";
    }

    @GetMapping(value = "/admin/indexCuochi")
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
        if (!cuocoBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors() && !cuocoService.existsByNomeAndCognome(cuoco.getNome(), cuoco.getCognome())) {
            try {
                // Salva il file e imposta l'URL dell'immagine
                String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
                cuoco.setUrlImage(fileUrl);

                // Salva le credenziali del cuoco
                credenziali.setCuoco(cuoco);
                credenzialiService.saveCredenziali(credenziali);
                credenziali.setRuolo(Credenziali.CUOCO_ROLE);

                cuocoService.save(cuoco);
                model.addAttribute("cuoco", cuoco);
                return "redirect:/admin/indexCuochi";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine");
                return "formRegisterCuoco.html";
            }
        } else {
            model.addAttribute("messaggioErrore", "Questo cuoco esiste già o ci sono errori nei dati inseriti");
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

    @PostMapping("/admin/delete/cuoco/{id}")
    public String deleteCuoco(@PathVariable("id") Long id) {
        Cuoco cuoco = cuocoService.findById(id);
        if (cuoco != null) {
            try {
                // Elimina il file associato al cuoco
                fileService.deleteFile(cuoco.getUrlImage(), UPLOADED_FOLDER);
                cuocoService.deleteById(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        Cuoco existingCuoco = cuocoService.findById(id);

        // Aggiorna i dettagli del cuoco
        existingCuoco.setNome(cuoco.getNome());
        existingCuoco.setCognome(cuoco.getCognome());
        existingCuoco.setDataDiNascita(cuoco.getDataDiNascita());

        if (!file.isEmpty()) {
            try {
                // Elimina il file esistente e salva il nuovo file
                fileService.deleteFile(existingCuoco.getUrlImage(), UPLOADED_FOLDER);
                String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
                existingCuoco.setUrlImage(fileUrl);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("messaggioErrore", "Errore nella gestione dell'immagine");
                return "admin/formModifyCuoco.html";
            }
        }

        cuocoService.save(existingCuoco);
        return "redirect:/admin/indexCuochi";
    }
}
