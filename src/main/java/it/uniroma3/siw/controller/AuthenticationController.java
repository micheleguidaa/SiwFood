package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CredenzialiService;

@Controller
public class AuthenticationController {
	@Autowired
	private CredenzialiService credenzialiService;

	@GetMapping(value = "/login")
	public String showLoginForm(Model model) {
		return "login.html";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credenziali credentials = credenzialiService.getCredenziali(userDetails.getUsername());
			if (credentials.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
				return "admin/indexAdmin.html";
			}
		}
        return "index.html";
	}

	
    
	@GetMapping(value="/success")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
		if(credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
			return "admin/indexAdmin.html";
		}
		return "success.html";
	}

	@GetMapping(value = "/registerCuoco")
	public String formNewCuoco(Model model) {
		model.addAttribute("cuoco", new Cuoco());
		model.addAttribute("credenziali", new Credenziali());
		return "formRegisterCuoco.html";
	}

}
