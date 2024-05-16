package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.repository.CuocoRepository;
import jakarta.transaction.Transactional;

@Service
public class CuocoService {
	@Autowired
	private CuocoRepository cuocoRepository;
	
	public Cuoco findById(Long id) {
		return cuocoRepository.findById(id).get();
	}
	
	public Iterable<Cuoco> findAll() {
		return cuocoRepository.findAll();
	}
	
	public boolean existsByNomeAndCognome(String nome,String cognome) {
		return cuocoRepository.existsByNomeAndCognome(nome,cognome);
	}
	
	public void save(Cuoco cuoco) {
		 cuocoRepository.save(cuoco);
	}
	
	@Transactional
	public void deleteById(Long id) {
		if(cuocoRepository.existsById(id)) {
		 cuocoRepository.deleteById(id);
		}
	}
}
