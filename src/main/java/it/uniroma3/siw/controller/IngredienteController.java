package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.service.IngredienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class IngredienteController {

	@Autowired
	private IngredienteService ingredienteService;

	@GetMapping("/addIngredienti")
	public String showAddIngredienteForm(HttpServletRequest request, HttpSession session,Model model) {
		String referer = request.getHeader("Referer");
		if (referer != null) {
			session.setAttribute("prevPage", referer);
		}
		return "addIngredienti";
	}

	@PostMapping("/addIngredienti")
	public String addIngrediente(@RequestParam("ingredienti") List<String> ingredienti,  HttpSession session, Model model) {
		ingredienteService.addIngredienti(ingredienti);
        // Reindirizza alla pagina precedente
        String prevPage = (String) session.getAttribute("prevPage");
        if (prevPage != null) {
            session.removeAttribute("prevPage"); // Rimuovi l'URL dalla sessione
            return "redirect:" + prevPage;
        }
        return "login";
	}

	@GetMapping("/admin/ingredienti")
	public String indexCuochi(Model model) {
		model.addAttribute("ingredienti", ingredienteService.findAllOrderedByNome());
		return "admin/indexIngredienti";
	}

}
