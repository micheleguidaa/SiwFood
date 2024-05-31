package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String searchCuochi(@RequestParam String stringa, Model model) {
        if (stringa.length() == 0) {
            model.addAttribute("cuochi", this.cuocoService.findAll());
            model.addAttribute("ricette", this.ricettaService.findAll());

        } else {
        	List<Cuoco> cuochiTrovati = this.cuocoService.findByNome(stringa);
        	cuochiTrovati.addAll(this.cuocoService.findByCognome(stringa));
            model.addAttribute("cuochi", cuochiTrovati);
            
        	List<Ricetta> ricetteTrovate = this.ricettaService.findByNome(stringa);
            model.addAttribute("ricette", ricetteTrovate);
        }
        return "redirect:/founds";
    }

    @GetMapping("/founds")
    public String foundCuochi(Model model) {
        model.addAttribute("cuochi", this.cuocoService.findAll());
        model.addAttribute("ricette", this.ricettaService.findAll());
        return "founds";
    }

}
