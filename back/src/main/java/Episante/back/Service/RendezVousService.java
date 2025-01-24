package Episante.back.Service ;

import Episante.back.Models.Disponibilite;
import Episante.back.Models.Medecin;
import Episante.back.Models.Patient;
import Episante.back.Models.RendezVous;
import Episante.back.Repository.DisponibiliteRepository;
import Episante.back.Repository.MedecinRepository;
import Episante.back.Repository.PatientRepository;
import Episante.back.Repository.RendezVousRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;
    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

    @Transactional // Use Transactional to ensure atomicity
    public RendezVous bookAppointment(Long medecinId, Long patientId, LocalDateTime dateTime) {
        // 1. Check if the doctor is available at that time

        Optional<Medecin> medecinOptional = medecinRepository.findById(medecinId);
        Optional<Patient> patientOptional = patientRepository.findById(patientId);

        if (medecinOptional.isEmpty() || patientOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid medecin or patient ID");
        }
        Medecin medecin = medecinOptional.get();
        Patient patient = patientOptional.get();

        //Check Disponibilite
        boolean isMedecinAvailable = medecin.getDisponibilites().stream()
                .anyMatch(disponibilite ->
                        dateTime.isAfter(disponibilite.getDebut()) && dateTime.isBefore(disponibilite.getFin()));

        if (!isMedecinAvailable) {
            throw new IllegalArgumentException("Medecin is not available at this time.");
        }

        // 2. Check for overlapping appointments.
        List<RendezVous> overlappingAppointments = rendezVousRepository.findOverlappingAppointments(medecinId, dateTime);

        if (!overlappingAppointments.isEmpty()) {
            throw new IllegalStateException("This time slot is already booked.");
        }

        // 3. Create and save the appointment
        RendezVous rendezVous = new RendezVous();
        rendezVous.setMedecin(medecin);
        rendezVous.setPatient(patient);
        rendezVous.setDateHeure(dateTime);

        return rendezVousRepository.save(rendezVous);
    }

    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    public Optional<RendezVous> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id);
    }

    public void deleteRendezVous(Long id) {
        rendezVousRepository.deleteById(id);
    }

    public List<Disponibilite> getAllDisponibilites() {
        return disponibiliteRepository.findAll();
    }

    public Disponibilite createDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }

    public Optional<Disponibilite> getDisponibiliteById(Long id) {
        return disponibiliteRepository.findById(id);
    }

    public void deleteDisponibilite(Long id) {
        disponibiliteRepository.deleteById(id);
    }
}
