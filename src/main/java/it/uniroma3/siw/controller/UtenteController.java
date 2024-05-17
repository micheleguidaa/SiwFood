package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.service.UtenteService;


@Controller
public class UtenteController {
	@Autowired
	private UtenteService utenteService;

	@GetMapping(value = "/admin/indexUtenti")
	public String indexUtenti(Model model) {
		model.addAttribute("utenti", utenteService.findAll());
		return "admin/indexUtenti.html";
	}
}