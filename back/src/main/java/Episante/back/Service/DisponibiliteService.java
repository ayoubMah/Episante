package Episante.back.Service;

import Episante.back.Models.Disponibilite;

import java.util.List;
import java.util.Optional;

public interface DisponibiliteService {
    List<Disponibilite> getAllDisponibilites();
    List<Disponibilite> getDisponibilitesByMedecin(Long medecinId);
    Optional<Disponibilite> getDisponibiliteById(Long id);
    Disponibilite createDisponibilite(Disponibilite disponibilite);
    Disponibilite updateDisponibilite(Long id, Disponibilite disponibilite);
    void deleteDisponibilite(Long id);
}
