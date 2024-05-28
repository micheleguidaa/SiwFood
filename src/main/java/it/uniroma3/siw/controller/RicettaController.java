package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.service.RicettaService;

@Controller
public class RicettaController {
	@Autowired
	private RicettaService ricettaService;

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
	public String showLeMieRicette( Model model) {
		model.addAttribute("ricette", ricettaService.findAll());
		return "cuoco/indexRicettePersonali.html";
	}
	
    @PostMapping("/delete/ricetta/{id}")
    public String deleteCuoco(@PathVariable("id") Long id) {
    	ricettaService.deleteById(id);
        return "redirect:/le-mie-ricette";
    }
}