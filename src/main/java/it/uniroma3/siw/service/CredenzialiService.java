package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;

@Service
public class CredenzialiService {
	
	@Autowired
    protected PasswordEncoder passwordEncoder;
	
	@Autowired
    private CredenzialiRepository credenzialiRepository;
	
	@Transactional
    public Credenziali getCredenziali(Long id) {
        return credenzialiRepository.findById(id).orElse(null);
    }

	@Transactional
    public Credenziali getCredenziali(String username) {
        return credenzialiRepository.findByUsername(username).orElse(null);
    }

	@Transactional
    public Credenziali saveCredenziali(Credenziali credenziali) {
		credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
        credenziali.setPassword(passwordEncoder.encode(credenziali.getPassword()));
        return credenzialiRepository.save(credenziali);
    }
}