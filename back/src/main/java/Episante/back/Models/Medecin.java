package Episante.back.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;       // Champ pour le nom du médecin
    private String specialite; // Champ pour la spécialité du médecin

    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL)
    private List<Disponibilite> disponibilites;

    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL)
    private List<RendezVous> rdvs;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public List<Disponibilite> getDisponibilites() {
        return disponibilites;
    }

    public void setDisponibilites(List<Disponibilite> disponibilites) {
        this.disponibilites = disponibilites;
    }

    public List<RendezVous> getRdvs() {
        return rdvs;
    }

    public void setRdvs(List<RendezVous> rdvs) {
        this.rdvs = rdvs;
    }
}
