package Episante.back.Controller;

import Episante.back.Models.Disponibilite;
import Episante.back.Models.RendezVous;
import Episante.back.Service.RendezVousService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rendezvous")
public class RendezVousController {

    @Autowired
    private RendezVousService rendezVousService;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody BookRequest request) {
        try {
            // Validate request body
            if (request.getMedecinId() == null || request.getPatientId() == null || request.getDateTime() == null) {
                return ResponseEntity.badRequest().body("All booking parameters are required");
            }

            RendezVous appointment = rendezVousService.bookAppointment(
                    request.getMedecinId(),
                    request.getPatientId(),
                    request.getDateTime()
            );

            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred: " + e.getMessage());
        }
    }

    // Inner class for request body
    public static class BookRequest {
        @NotNull(message = "Doctor ID is required")
        private Long medecinId;

        @NotNull(message = "Patient ID is required")
        private Long patientId;

        @NotNull(message = "Date/Time is required")
        @Future(message = "Appointment must be in the future")
        private LocalDateTime dateTime;

        // Getters and Setters
        public Long getMedecinId() { return medecinId; }
        public void setMedecinId(Long medecinId) { this.medecinId = medecinId; }

        public Long getPatientId() { return patientId; }
        public void setPatientId(Long patientId) { this.patientId = patientId; }

        public LocalDateTime getDateTime() { return dateTime; }
        public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    }

    @GetMapping
    public List<RendezVous> getAllRendezVous() {
        return rendezVousService.getAllRendezVous();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendezVous> getRendezVousById(@PathVariable Long id) {
        Optional<RendezVous> rendezVous = rendezVousService.getRendezVousById(id);
        return rendezVous.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRendezVous(@PathVariable Long id) {
        rendezVousService.deleteRendezVous(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibilites")
    public List<Disponibilite> getAllDisponibilites() {
        return rendezVousService.getAllDisponibilites();
    }

    @PostMapping("/disponibilites")
    public Disponibilite createDisponibilite(@RequestBody Disponibilite disponibilite) {
        return rendezVousService.createDisponibilite(disponibilite);
    }

    @GetMapping("/disponibilites/{id}")
    public ResponseEntity<Disponibilite> getDisponibiliteById(@PathVariable Long id) {
        Optional<Disponibilite> disponibilite = rendezVousService.getDisponibiliteById(id);
        return disponibilite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/disponibilites/{id}")
    public ResponseEntity<Void> deleteDisponibilite(@PathVariable Long id) {
        rendezVousService.deleteDisponibilite(id);
        return ResponseEntity.noContent().build();
    }
}
