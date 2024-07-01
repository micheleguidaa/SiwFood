package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

@Component
public class IngredienteValidator implements Validator {
	@Autowired
	private IngredienteRepository IngredienteRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Ingrediente Ingrediente = (Ingrediente)o;
		if (Ingrediente.getNome()!=null 
				&& IngredienteRepository.existsByNome(Ingrediente.getNome())) {
			errors.reject("Ingrediente.duplicate");
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Ingrediente.class.equals(aClass);
	}
}