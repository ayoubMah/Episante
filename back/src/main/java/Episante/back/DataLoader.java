package Episante.back;

import Episante.back.Models.*;
import Episante.back.Repository.MedecinRepository;
import Episante.back.Repository.PatientRepository;
import Episante.back.Repository.QuestionRepository;
import Episante.back.Service.RendezVousService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader {

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RendezVousService rendezVousService;

    @PostConstruct
    public void loadData() {
        // Create some doctors
        Medecin medecin1 = new Medecin();
        medecin1.setNom("Dr. Smith");
        medecin1.setSpecialite("Cardiologist");
        medecinRepository.save(medecin1);

        Medecin medecin2 = new Medecin();
        medecin2.setNom("Dr. Johnson");
        medecin2.setSpecialite("Dermatologist");
        medecinRepository.save(medecin2);

        Medecin medecin3 = new Medecin();
        medecin3.setNom("Dr. Williams");
        medecin3.setSpecialite("Pediatrician");
        medecinRepository.save(medecin3);

        // Create some availabilities for Dr. Smith
        Disponibilite disponibilite1 = new Disponibilite();
        disponibilite1.setDebut(LocalDateTime.of(2024, 3, 5, 9, 0));
        disponibilite1.setFin(LocalDateTime.of(2024, 3, 5, 12, 0));
        disponibilite1.setMedecin(medecin1);
        rendezVousService.createDisponibilite(disponibilite1);


        Disponibilite disponibilite2 = new Disponibilite();
        disponibilite2.setDebut(LocalDateTime.of(2024, 3, 6, 14, 0));
        disponibilite2.setFin(LocalDateTime.of(2024, 3, 6, 17, 0));
        disponibilite2.setMedecin(medecin1);
        rendezVousService.createDisponibilite(disponibilite2);

        // Create some availabilities for Dr. Johnson
        Disponibilite disponibilite3 = new Disponibilite();
        disponibilite3.setDebut(LocalDateTime.of(2024, 3, 5, 10, 0));
        disponibilite3.setFin(LocalDateTime.of(2024, 3, 5, 11, 0));
        disponibilite3.setMedecin(medecin2);
        rendezVousService.createDisponibilite(disponibilite3);

        Disponibilite disponibilite4 = new Disponibilite();
        disponibilite4.setDebut(LocalDateTime.of(2024, 3, 7, 13, 0));
        disponibilite4.setFin(LocalDateTime.of(2024, 3, 7, 16, 0));
        disponibilite4.setMedecin(medecin2);
        rendezVousService.createDisponibilite(disponibilite4);


        // Create some patients
        Patient patient1 = new Patient();
        patient1.setNom("Alice");
        patient1.setPrenom("Wonderland");
        patient1.setAge(30);
        patient1.setPoids(65.0);
        patient1.setSexe(Sexe.FEMME);
        patientRepository.save(patient1);


        Patient patient2 = new Patient();
        patient2.setNom("Bob");
        patient2.setPrenom("Builder");
        patient2.setAge(45);
        patient2.setPoids(80.0);
        patient2.setSexe(Sexe.HOMME);
        patientRepository.save(patient2);

    }
}