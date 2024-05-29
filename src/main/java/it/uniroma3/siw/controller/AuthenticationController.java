package it.uniroma3.siw.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.CuocoService;

@Controller
public class AuthenticationController {
	@Autowired
	private CredenzialiService credenzialiService;
	
    @Autowired
    private CuocoService cuocoService;

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login.html";
	}

	@GetMapping("/") 
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

	
	@GetMapping("/success")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
		if(credenziali.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
			return "admin/indexAdmin";
		}
		return "success.html";
	}

	@GetMapping("/registerCuoco")
	public String formNewCuoco(Model model) {
		model.addAttribute("cuoco", new Cuoco());
		model.addAttribute("credenziali", new Credenziali());
		return "formRegisterCuoco";
	}
	

    @PostMapping("/registerCuoco")
    public String registerCuoco(@ModelAttribute("cuoco") Cuoco cuoco,
                                BindingResult cuocoBindingResult, 
                                @ModelAttribute("credenziali") Credenziali credenziali,
                                BindingResult credenzialiBindingResult,
                                @RequestParam("fileImage") MultipartFile file,
                                Model model) {
        if (cuocoBindingResult.hasErrors() || credenzialiBindingResult.hasErrors()) {
            model.addAttribute("messaggioErrore", "Questo cuoco esiste già o ci sono errori nei dati inseriti");
            return "formRegisterCuoco";
        }
        try {
            cuocoService.registerCuoco(cuoco, credenziali, file);
            credenzialiService.saveCredenziali(credenziali);
            return "redirect:/login";
        } catch (IOException e) {
            model.addAttribute("messaggioErrore", "Errore nel caricamento dell'immagine");
            return "formRegisterCuoco";
        }
    }
}
