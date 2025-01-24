package Episante.back.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

// Cette annotation de JPA (Java Persistence API) indique que cette classe est une entité
@Entity
public class Patient {

    // Cette annotation marque le champ id comme étant la clé primaire de l'entité
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String identifier;

    private String nom;
    private String prenom;
    private int age;
    private double poids;
    private double currentWeight;
    private Double height;

    @Enumerated(EnumType.STRING) // store enum as,string
    private Sexe sexe;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }


    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<RendezVous> rdvs ;

    public List<RendezVous> getRdvs(){
        return rdvs ;
    }

    public void setRdvs(List<RendezVous> rdvs) {
        this.rdvs = rdvs;
    }
}
