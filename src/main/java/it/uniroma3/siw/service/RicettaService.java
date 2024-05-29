package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.RigaRicetta;
import it.uniroma3.siw.repository.RicettaRepository;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RicettaService {

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private CuocoService cuocoService;

    @Autowired
    private FileService fileService;

    @Autowired
    private IngredienteService ingredienteService;

    private static final String UPLOAD_DIR = "uploads/ricette2/";

    public Ricetta findById(Long id) {
        return ricettaRepository.findById(id).orElse(null);
    }

    public Iterable<Ricetta> findAll() {
        return ricettaRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        ricettaRepository.deleteById(id);
    }

    public void save(Ricetta ricetta) {
        ricettaRepository.save(ricetta);
    }

    @Transactional
    public void registerRicetta(Ricetta ricetta, Long cuocoId, MultipartFile[] files, List<Long> ingredientiIds, List<String> quantitaList) throws IOException {
        Cuoco cuoco = cuocoService.findById(cuocoId);
        ricetta.setCuoco(cuoco);

        List<String> urlsImages = handleFileUpload(files);
        ricetta.setUrlsImages(urlsImages);

        List<RigaRicetta> righeRicetta = new ArrayList<>();
        for (int i = 0; i < ingredientiIds.size(); i++) {
            Ingrediente ingrediente = ingredienteService.findById(ingredientiIds.get(i));
            if (ingrediente != null) {
                RigaRicetta riga = new RigaRicetta();
                riga.setIngrediente(ingrediente);
                riga.setQuantita(quantitaList.get(i));
                riga.setRicetta(ricetta);
                righeRicetta.add(riga);
            }
        }
        ricetta.setRigheRicetta(righeRicetta);

        save(ricetta);
    }

    @Transactional
    public void updateRicetta(Long id, Ricetta updatedRicetta, MultipartFile[] files, List<Long> ingredientiIds, List<String> quantitaList) throws IOException {
        Ricetta existingRicetta = findById(id);
        if (existingRicetta != null) {
            existingRicetta.setNome(updatedRicetta.getNome());
            existingRicetta.setDescrizione(updatedRicetta.getDescrizione());

            List<String> urlsImages = handleFileUpload(files);
            if (!urlsImages.isEmpty()) {
                existingRicetta.setUrlsImages(urlsImages);
            }

            List<RigaRicetta> righeRicetta = new ArrayList<>();
            for (int i = 0; i < ingredientiIds.size(); i++) {
                Ingrediente ingrediente = ingredienteService.findById(ingredientiIds.get(i));
                if (ingrediente != null) {
                    RigaRicetta riga = new RigaRicetta();
                    riga.setIngrediente(ingrediente);
                    riga.setQuantita(quantitaList.get(i));
                    riga.setRicetta(existingRicetta);
                    righeRicetta.add(riga);
                }
            }
            existingRicetta.setRigheRicetta(righeRicetta);

            save(existingRicetta);
        }
    }
    
    @Transactional
    public void updateRicetta(Long id, Ricetta updatedRicetta, MultipartFile[] files) throws IOException {
        Ricetta existingRicetta = findById(id);
        if (existingRicetta != null) {
            existingRicetta.setNome(updatedRicetta.getNome());
            existingRicetta.setDescrizione(updatedRicetta.getDescrizione());

            List<String> urlsImages = handleFileUpload(files);
            if (!urlsImages.isEmpty()) {
                existingRicetta.setUrlsImages(urlsImages);
            }

            // Aggiornamento delle righe ricetta
            List<RigaRicetta> newRigheRicetta = updatedRicetta.getRigheRicetta();
            List<RigaRicetta> existingRigheRicetta = existingRicetta.getRigheRicetta();

            // Rimuovi righe che non sono presenti nel nuovo elenco
            existingRigheRicetta.removeIf(existingRiga -> newRigheRicetta.stream()
                    .noneMatch(newRiga -> newRiga.getId() != null && newRiga.getId().equals(existingRiga.getId())));

            // Aggiungi o aggiorna righe
            for (RigaRicetta newRiga : newRigheRicetta) {
                if (newRiga.getId() != null) {
                    // Aggiorna riga esistente
                    RigaRicetta existingRiga = existingRigheRicetta.stream()
                            .filter(r -> r.getId().equals(newRiga.getId()))
                            .findFirst()
                            .orElse(null);
                    if (existingRiga != null) {
                        existingRiga.setIngrediente(newRiga.getIngrediente());
                        existingRiga.setQuantita(newRiga.getQuantita());
                    }
                } else {
                    // Aggiungi nuova riga
                    newRiga.setRicetta(existingRicetta);
                    existingRigheRicetta.add(newRiga);
                }
            }

            save(existingRicetta);
        }
    }

    private List<String> handleFileUpload(MultipartFile[] files) throws IOException {
        List<String> urlsImages = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String imageUrl = fileService.saveFile(file, UPLOAD_DIR);
                urlsImages.add(imageUrl);
            }
        }
        return urlsImages;
    }

}
