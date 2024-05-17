package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {
	
	@Autowired
    private UtenteRepository utenteRepository;

    public Utente getUtente(Long id) {
        return utenteRepository.findById(id).orElse(null);
    }

    public Utente saveUtente(Utente utente) {
        return utenteRepository.save(utente);
    }
    
	public Iterable<Utente> findAll() {
		return utenteRepository.findAll();
	}

}