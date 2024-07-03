package it.uniroma3.siw.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ricetta;


public interface RicettaRepository extends CrudRepository<Ricetta, Long>{
    public List<Ricetta> findByNomeStartingWithIgnoreCase(String nome);
	public boolean existsByNome(String nome);

	
}
