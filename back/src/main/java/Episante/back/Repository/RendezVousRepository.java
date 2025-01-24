package Episante.back.Repository;

import Episante.back.Models.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

    // Custom query to check for overlapping appointments for a given doctor and time
    @Query("SELECT rv FROM RendezVous rv WHERE rv.medecin.id = :medecinId AND rv.dateHeure = :dateTime")
    List<RendezVous> findOverlappingAppointments(@Param("medecinId") Long medecinId, @Param("dateTime") LocalDateTime dateTime);
}