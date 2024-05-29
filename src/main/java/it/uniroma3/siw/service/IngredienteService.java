package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

import java.util.List;

@Service
public class IngredienteService {
    @Autowired
    private IngredienteRepository ingredienteRepository;

    public Iterable<Ingrediente> findAll() {
        return ingredienteRepository.findAll();
    }

    public void addIngredienti(List<String> ingredienti) {
        for (String nomeIngrediente : ingredienti) {
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setNome(nomeIngrediente);
            ingredienteRepository.save(ingrediente);
        }
    }
}
