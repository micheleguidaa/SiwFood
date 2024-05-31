package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;

@Controller
public class SearchController {
	
	@Autowired
	private RicettaService ricettaService;
	
    @Autowired
    private CuocoService cuocoService;
	
    @PostMapping("/search")
    public String searchCuochi(@RequestParam String stringa, RedirectAttributes redirectAttributes) {
        if (stringa.length() == 0) {
            redirectAttributes.addFlashAttribute("cuochi", this.cuocoService.findAll());
            redirectAttributes.addFlashAttribute("ricette", this.ricettaService.findAll());
        } else {
            List<Cuoco> cuochiTrovati = this.cuocoService.findByNome(stringa);
            cuochiTrovati.addAll(this.cuocoService.findByCognome(stringa));
            redirectAttributes.addFlashAttribute("cuochi", cuochiTrovati);
            
            List<Ricetta> ricetteTrovate = this.ricettaService.findByNome(stringa);
            redirectAttributes.addFlashAttribute("ricette", ricetteTrovate);
        }
        return "redirect:/founds";
    }

    @GetMapping("/founds")
    public String foundCuochi(Model model) {
        return "founds";
    }

}
