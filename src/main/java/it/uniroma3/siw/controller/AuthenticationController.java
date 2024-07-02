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

import it.uniroma3.siw.controller.validator.CredenzialiValidator;
import it.uniroma3.siw.controller.validator.CuocoValidator;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	@Autowired
	private CredenzialiService credenzialiService;
	
    @Autowired
    private CuocoService cuocoService;
        
    @Autowired
    private CuocoValidator cuocoValidator;
    
    @Autowired
    private CredenzialiValidator credenzialiValidator;

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login";
	}

	@GetMapping("/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credenziali credentials = credenzialiService.getCredenziali(userDetails.getUsername());
			if (credentials.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
				return "admin/indexAdmin";
			}
		}
        return "index";
	}

	@GetMapping("/success")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credentials = credenzialiService.getCredenziali(userDetails.getUsername());
		if(credentials.getRuolo().equals(Credenziali.ADMIN_ROLE)) {
			return "admin/indexAdmin";
		}
		return "success";
	}

	@GetMapping("/registerCuoco")
	public String formNewCuoco(HttpServletRequest request, HttpSession session, Model model) {
	    String referer = request.getHeader("Referer");
	    if (referer != null) {
	        session.setAttribute("prevPage", referer);
	    }
	    model.addAttribute("cuoco", new Cuoco());
	    model.addAttribute("credenziali", new Credenziali());
	    return "formRegisterCuoco";
	}
	

	@PostMapping("/registerCuoco")
	public String registerCuoco(@Valid @ModelAttribute("cuoco") Cuoco cuoco,
	                            BindingResult cuocoBindingResult, 
	                            @ModelAttribute("credenziali") Credenziali credenziali,
	                            BindingResult credenzialiBindingResult,
	                            @RequestParam("fileImage") MultipartFile file,
	                            HttpSession session,
	                            Model model) {
		
	    this.cuocoValidator.validate(cuoco, cuocoBindingResult);
	    this.credenzialiValidator.validate(credenziali, credenzialiBindingResult);
	    
	    
	    // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
	    if (!cuocoBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
	        try {
	            cuocoService.registerCuoco(cuoco, credenziali, file);
	        } catch (IOException e) {
	            e.printStackTrace();
	            model.addAttribute("fileUploadError", "Errore nel caricamento dell'immagine");
	            return "formRegisterCuoco";
	        }
	        model.addAttribute("cuoco", cuoco);
	        
	        // Reindirizza alla pagina precedente
	        String prevPage = (String) session.getAttribute("prevPage");
	        if (prevPage != null) {
	            session.removeAttribute("prevPage"); // Rimuovi l'URL dalla sessione
	            return "redirect:" + prevPage;
	        }
	        return "login";
	    }
	    return "formRegisterCuoco";
	}

}
