package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CuocoService;

@Controller
public class CuocoController {
	@Autowired
	private CuocoService cuocoService;
	
	

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
	public String newCuoco(@ModelAttribute("cuoco") Cuoco cuoco, Model model) {
		if (!cuocoService.existsByNomeAndCognome(cuoco.getNome(), cuoco.getCognome())) {
			this.cuocoService.save(cuoco); 
			model.addAttribute("cuoco", cuoco);
			return "cuoco.html";
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


}
