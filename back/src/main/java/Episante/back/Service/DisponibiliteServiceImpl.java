package Episante.back.Service;

import Episante.back.Models.Disponibilite;
import Episante.back.Repository.DisponibiliteRepository;
import Episante.back.Service.DisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisponibiliteServiceImpl implements DisponibiliteService {

    private final DisponibiliteRepository disponibiliteRepository;

    @Autowired
    public DisponibiliteServiceImpl(DisponibiliteRepository disponibiliteRepository) {
        this.disponibiliteRepository = disponibiliteRepository;
    }

    @Override
    public List<Disponibilite> getAllDisponibilites() {
        return disponibiliteRepository.findAll();
    }

    @Override
    public List<Disponibilite> getDisponibilitesByMedecin(Long medecinId) {
        return disponibiliteRepository.findByMedecinId(medecinId);
    }

    @Override
    public Optional<Disponibilite> getDisponibiliteById(Long id) {
        return disponibiliteRepository.findById(id);
    }

    @Override
    public Disponibilite createDisponibilite(Disponibilite disponibilite) {
        return disponibiliteRepository.save(disponibilite);
    }

    @Override
    public Disponibilite updateDisponibilite(Long id, Disponibilite disponibilite) {
        disponibilite.setId(id);
        return disponibiliteRepository.save(disponibilite);
    }

    @Override
    public void deleteDisponibilite(Long id) {
        disponibiliteRepository.deleteById(id);
    }
}
