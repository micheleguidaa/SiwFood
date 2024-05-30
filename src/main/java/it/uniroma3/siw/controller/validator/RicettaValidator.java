package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.RicettaRepository;

/***********************************************
 				DA FIXARE!!!
 ***********************************************/



@Component
public class RicettaValidator implements Validator {
    @Autowired
    private RicettaRepository ricettaRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Ricetta.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	Ricetta ricetta = (Ricetta) target;
        if (ricetta.getNome() != null && ricetta.getCuoco() != null && this.ricettaRepository.existsByNomeAndCuoco(ricetta.getNome(), ricetta.getCuoco())) {
            errors.reject("ricetta.duplicate");
        }
    }

}
