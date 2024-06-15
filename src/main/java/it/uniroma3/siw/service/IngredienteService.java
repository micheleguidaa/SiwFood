package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void addIngredienti(List<String> ingredienti) {
        for (String nomeIngrediente : ingredienti) {
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setNome(nomeIngrediente);
            ingredienteRepository.save(ingrediente);
        }
    }
    
    public Ingrediente findById(Long id) {
        return ingredienteRepository.findById(id).orElse(null);
    }
    
    public List<Ingrediente> findAllOrderedByNome() {
        return ingredienteRepository.findAllByOrderByNomeAsc();
    }

    @Transactional
    public void deleteById(Long id) {
        ingredienteRepository.deleteById(id);
    }
}