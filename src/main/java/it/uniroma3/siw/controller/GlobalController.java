package it.uniroma3.siw.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.userdetails.UserDetails;

@ControllerAdvice
public class GlobalController {
	@ModelAttribute("UserDetails")
	
	public UserDetails getUtente() {
		UserDetails  utente = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			utente = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();}
		return utente;
	}
}