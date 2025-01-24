package Episante.back.Controller;

import Episante.back.Models.Medecin;
import Episante.back.Repository.RendezVousRepository;
import Episante.back.Service.ImedecinService;
import Episante.back.Service.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medecins")
public class MedecinController {

    private static final Logger logger = LoggerFactory.getLogger(MedecinController.class);
    private final ImedecinService medecinService;

    @Autowired
    public MedecinController(ImedecinService medecinService) {
        this.medecinService = medecinService;
    }

    @GetMapping
    public ResponseEntity<List<Medecin>> getAllMedecins() {
        logger.info("Fetching all medecins");
        return ResponseEntity.ok(medecinService.getAllMedecins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medecin> getMedecinById(@PathVariable Long id) {
        logger.info("Fetching medecin with id: {}", id);
        Optional<Medecin> medecin = medecinService.getMedecinById(id);
        return medecin.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Medecin> createMedecin(@RequestBody Medecin medecin) {
        logger.info("Creating new medecin: {}", medecin);
        Medecin createdMedecin = medecinService.createMedecin(medecin);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMedecin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medecin> updateMedecin(@PathVariable Long id, @RequestBody Medecin medecin) {
        logger.info("Updating medecin with id {}: {}", id, medecin);
        Optional<Medecin> existingMedecin = medecinService.getMedecinById(id);
        if (existingMedecin.isPresent()) {
            Medecin updatedMedecin = medecinService.updateMedecin(id, medecin);
            return ResponseEntity.ok(updatedMedecin);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedecin(@PathVariable Long id) {
        logger.info("Deleting medecin with id: {}", id);
        Optional<Medecin> medecin = medecinService.getMedecinById(id);
        if (medecin.isPresent()) {
            medecinService.deleteMedecin(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}