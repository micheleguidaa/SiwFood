package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long>{
	public List<Ingrediente> findAllByOrderByNomeAsc();
	public boolean existsByNome(String nome);
}
