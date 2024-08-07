package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;

@Controller
public class RicettaController {

	@Autowired
	private RicettaService ricettaService;

	@Autowired
	private IngredienteService ingredienteService;
	

	@GetMapping("/ricette")
	public String showRicette(Model model) {
		model.addAttribute("ricette", ricettaService.getAllRicette());
		return "ricette";
	}

	@GetMapping("/admin/ricette")
	public String indexRicette(Model model) {
		model.addAttribute("ricette", ricettaService.getAllRicette());
		return "admin/indexRicette";
	}

	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", ricettaService.getRicetta(id));
		return "ricetta";
	}
	
	@GetMapping("/addRicetta")
	public String addRicetta(Model model) {
		model.addAttribute("ricetta", new Ricetta());
		model.addAttribute("ingredienti", ingredienteService.findAllOrderedByNome());
		return "cuoco/addRicetta";
	}
	
	@PostMapping("/addRicetta")
	public String addRicetta(@Valid @ModelAttribute("ricetta") Ricetta ricetta, BindingResult ricettaBindingResult,
			@RequestParam("fileImages") MultipartFile[] files, @RequestParam("cuocoId") Long cuocoId,
			@RequestParam("ingredientiIds") List<Long> ingredientiIds,
			@RequestParam("quantita") List<String> quantitaList, Model model) {
		if (ricettaBindingResult.hasErrors()) {
			model.addAttribute("ricetta", new Ricetta());
			model.addAttribute("ingredienti", ingredienteService.findAllOrderedByNome());
			return "cuoco/addRicetta";
	
		}
		
		try {
			ricettaService.registerRicetta(ricetta, cuocoId, files, ingredientiIds, quantitaList);
			return "redirect:/leMieRicette";
		} catch (IOException e) {
			return "cuoco/addRicetta";
		}
	
	}


	@GetMapping("/update/ricetta/{id}")
	public String formModifyRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta", ricettaService.getRicetta(id));
		model.addAttribute("ingredienti", ingredienteService.findAllOrderedByNome());
		return "cuoco/formModifyRicetta";
	}

	@PostMapping("/update/ricetta/{id}")
	public String updateRicetta(@PathVariable("id") Long id, @ModelAttribute("ricetta") Ricetta ricetta,
			BindingResult ricettaBindingResult, @RequestParam("fileImages") MultipartFile[] files,
			@RequestParam("ingredientiIds") List<Long> ingredientiIds,
			@RequestParam("quantita") List<String> quantitaList, Model model) {
		if (ricettaBindingResult.hasErrors()) {
			List<Ingrediente> ingredienti = (List<Ingrediente>) ingredienteService.findAll();
			model.addAttribute("ingredienti", ingredienti);
			return "cuoco/formModifyRicetta";
		}

		try {
			ricettaService.updateRicetta(id, ricetta, files, ingredientiIds, quantitaList);
			return "redirect:/leMieRicette";
		} catch (IOException e) {
			model.addAttribute("messaggioErrore", "Errore nel caricamento delle immagini");
			return "cuoco/formModifyRicetta";
		}
	}

	@GetMapping("/leMieRicette")
	public String showLeMieRicette(Model model) {
		model.addAttribute("ricette", ricettaService.getAllRicette());
		return "cuoco/indexLeMieRicette";
	}

	@PostMapping("/delete/ricetta/{id}")
	public String deleteRicetta(@PathVariable("id") Long id) {
		ricettaService.deleteById(id);
		return "redirect:/leMieRicette";
	}

	@PostMapping("/admin/delete/ricetta/{id}")
	public String deleteRicettaByAdmin(@PathVariable("id") Long id) {
		ricettaService.deleteById(id);
		return "redirect:/admin/ricette";
	}

}

