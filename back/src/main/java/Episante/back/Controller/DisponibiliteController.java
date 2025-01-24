package Episante.back.Controller;

import Episante.back.Models.Disponibilite;
import Episante.back.Service.DisponibiliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("/disponibilites")
public class DisponibiliteController {

    private static final Logger logger = LoggerFactory.getLogger(DisponibiliteController.class);
    private final DisponibiliteService disponibiliteService;

    @Autowired
    public DisponibiliteController(DisponibiliteService disponibiliteService) {
        this.disponibiliteService = disponibiliteService;
    }

    @GetMapping
    public ResponseEntity<List<Disponibilite>> getAllDisponibilites(
            @RequestParam(required = false) Long medecinId) {
        if (medecinId != null) {
            logger.info("Fetching disponibilites for medecin ID: {}", medecinId);
            return ResponseEntity.ok(disponibiliteService.getDisponibilitesByMedecin(medecinId));
        }
        logger.info("Fetching all disponibilites");
        return ResponseEntity.ok(disponibiliteService.getAllDisponibilites());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disponibilite> getDisponibiliteById(@PathVariable Long id) {
        logger.info("Fetching disponibilite with ID: {}", id);
        Optional<Disponibilite> disponibilite = disponibiliteService.getDisponibiliteById(id);
        return disponibilite.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Disponibilite> createDisponibilite(@RequestBody Disponibilite disponibilite) {
        logger.info("Creating new disponibilite: {}", disponibilite);
        Disponibilite createdDisponibilite = disponibiliteService.createDisponibilite(disponibilite);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDisponibilite);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disponibilite> updateDisponibilite(
            @PathVariable Long id, @RequestBody Disponibilite disponibilite) {
        logger.info("Updating disponibilite with ID {}: {}", id, disponibilite);
        Optional<Disponibilite> existingDisponibilite = disponibiliteService.getDisponibiliteById(id);
        if (existingDisponibilite.isPresent()) {
            Disponibilite updatedDisponibilite = disponibiliteService.updateDisponibilite(id, disponibilite);
            return ResponseEntity.ok(updatedDisponibilite);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisponibilite(@PathVariable Long id) {
        logger.info("Deleting disponibilite with ID: {}", id);
        Optional<Disponibilite> disponibilite = disponibiliteService.getDisponibiliteById(id);
        if (disponibilite.isPresent()) {
            disponibiliteService.deleteDisponibilite(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}