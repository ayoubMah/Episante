package Episante.back.Repository;

import Episante.back.Models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPatientDao extends JpaRepository<Patient, Long> {
    Optional<Patient> findByIdentifier(String identifier);
}

// L'interface IPatientDao est responsable de l'accès aux données pour l'entité Patient

//Nore interface étend JpaRepository de Spring Data JPA.
// C'est une interface puissante qui fournit déjà de nombreuses méthodes pour les opérations CRUD de base