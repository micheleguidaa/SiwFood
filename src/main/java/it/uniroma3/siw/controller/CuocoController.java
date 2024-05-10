package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.CuocoService;

@Controller
public class CuocoController {
	@Autowired
	private CuocoService cuocoService;
	
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


}
