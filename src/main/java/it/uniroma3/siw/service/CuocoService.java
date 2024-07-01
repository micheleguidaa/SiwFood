package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CuocoRepository;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuocoService {

    @Autowired
    private CuocoRepository cuocoRepository;

    @Autowired
    private FileService fileService;
    
    @Autowired
    private CredenzialiService credenzialiService;

    private static final String UPLOADED_FOLDER = "uploads/cuochi2/";
    private static final String DEFAULT_IMAGE = "/images/default/senzaFoto.jpeg";
    
    /**
     * This method retrieves a Cuoco from the DB based on its ID.
     * @param id the id of the Cuoco to retrieve from the DB
     * @return the retrieved Cuoco, or null if no Cuoco with the passed ID could be found in the DB
     */
    @Transactional
    public Cuoco getCuoco(Long id) {
        Optional<Cuoco> result = this.cuocoRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method retrieves all Cuochi from the DB.
     * @return a List with all the retrieved Cuochi
     */
    @Transactional
    public List<Cuoco> getAllCuochi() {
        List<Cuoco> result = new ArrayList<>();
        Iterable<Cuoco> iterable = this.cuocoRepository.findAll();
        for(Cuoco Cuoco : iterable)
            result.add(Cuoco);
        return result;
    }
 

    @Transactional
    public void save(Cuoco cuoco) {
        cuocoRepository.save(cuoco);
    }

    @Transactional
    public void deleteById(Long id) {
        Cuoco cuoco = cuocoRepository.findById(id).orElse(null);
        if (cuoco != null) {
            try {
                fileService.deleteFile(cuoco.getUrlImage(), UPLOADED_FOLDER);
                cuocoRepository.deleteById(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Cuoco non trovato con id: " + id);
        }
    }
    @Transactional
    public void registerCuoco(Cuoco cuoco, Credenziali credenziali, MultipartFile file) throws IOException {
    	
            if (file.isEmpty()) {
                cuoco.setUrlImage(DEFAULT_IMAGE);
            } else {
                String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
                cuoco.setUrlImage(fileUrl);
            }
            credenziali.setCuoco(cuoco);
            credenziali.setRuolo(Credenziali.DEFAULT_ROLE);
            
            save(cuoco);
            credenzialiService.saveCredenziali(credenziali); 
    }

    @Transactional
    public void updateCuoco(Long id, Cuoco updatedCuoco, MultipartFile file) throws IOException {
        Cuoco existingCuoco = getCuoco(id);
        if (existingCuoco != null) {
            updateCuocoDetails(existingCuoco, updatedCuoco);

            if (!file.isEmpty()) {
                fileService.deleteFile(existingCuoco.getUrlImage(), UPLOADED_FOLDER);
                String fileUrl = fileService.saveFile(file, UPLOADED_FOLDER);
                existingCuoco.setUrlImage(fileUrl);
            }

            save(existingCuoco);
        }
    }

    @Transactional
    private void updateCuocoDetails(Cuoco existingCuoco, Cuoco updatedCuoco) {
        existingCuoco.setNome(updatedCuoco.getNome());
        existingCuoco.setCognome(updatedCuoco.getCognome());
        existingCuoco.setDataDiNascita(updatedCuoco.getDataDiNascita());
    }

    
    @Transactional
    public List<Cuoco> findByNome(String nome) {
    	return cuocoRepository.findByNomeStartingWithIgnoreCase(nome);
    }
    
    @Transactional
    public List<Cuoco> findByCognome(String cognome) {
    	return cuocoRepository.findByCognomeStartingWithIgnoreCase(cognome);
    }
    
    @Transactional
    public List<Cuoco> findByNomeAndCognome(String nome, String cognome) {
        return cuocoRepository.findByNomeStartingWithIgnoreCaseAndCognomeStartingWithIgnoreCase(nome, cognome);
    }
}
