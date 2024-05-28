package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

import java.util.List;

@Controller
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping("/addIngredienti")
    public String showAddIngredienteForm(Model model) {
        return "addIngredienti";
    }

    @PostMapping("/aggiungiIngredienti")
    public String addIngrediente(@RequestParam("ingredienti") List<String> ingredienti, Model model) {
        for (String nomeIngrediente : ingredienti) {
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setNome(nomeIngrediente);
            ingredienteRepository.save(ingrediente);
        }

        // Redirect to the previous page URL
        return "redirect:/";
    }
}
