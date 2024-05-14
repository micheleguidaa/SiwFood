package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;

@Service
public class CredenzialiService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredenzialiRepository credentialsRepository;

    @Transactional
    public Credenziali getCredentiziali(Long id) {
        Optional<Credenziali> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Credenziali getCredenziali(String username) {
        Optional<Credenziali> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }

    @Transactional
    public Credenziali saveCredenziali(Credenziali credentials) {
        credentials.setRuolo(Credenziali.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
}
