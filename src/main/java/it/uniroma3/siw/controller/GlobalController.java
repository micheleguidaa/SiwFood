package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CredenzialiService;

import org.springframework.security.core.userdetails.UserDetails;

@ControllerAdvice
public class GlobalController {
	@Autowired
	private CredenzialiService credenzialiService;
	
	@ModelAttribute("userDetails")
	public UserDetails getUtente() {
		UserDetails  utente = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			utente = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();}
		return utente;
	}
	
    @ModelAttribute("ruolo")
    public String getAuthorityAsString() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getAuthorities().isEmpty()) {
            GrantedAuthority authority = authentication.getAuthorities().iterator().next();
            return authority.getAuthority();
        }
        return null;
    }
    
    
    @ModelAttribute("cuocoCorrente")
    public Cuoco getCuoco() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() 
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
            if (credenziali != null) {
                return credenziali.getCuoco();
            }
        }
        return null;
    }
}