package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entité Species (Espèces)
 * Représente une espèce animale
 * 
 * Table: species
 * Colonnes:
 *   - id (PK): Identifiant unique
 *   - common_name: Nom commun (Chat, Chien, Lapin)
 *   - latin_name: Nom scientifique en Latin
 * 
 * Relations:
 *   - OneToMany avec Animal (une espèce peut avoir plusieurs animaux)
 * 
 * @author Moneli AGAMAKA IRA2026
 * @version 1.0
 */
@Entity
@Table(name = "species")
public class Species {

    /**
     * Identifiant unique de l'espèce
     * Type: INT, AutoIncrement, Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom commun de l'espèce
     * Type: VARCHAR(50), NOT NULL
     * Exemples: Chat, Chien, Lapin
     */
    @Column(name = "common_name", nullable = false, length = 50)
    private String commonName;

    /**
     * Nom scientifique en Latin
     * Type: VARCHAR(200), NOT NULL
     * Exemples: Felis silvestris catus, Canis lupus familiaris
     */
    @Column(name = "latin_name", nullable = false, length = 200)
    private String latinName;

    /**
     * Relation OneToMany vers Animal
     * Une espèce peut avoir plusieurs animaux
     * mappedBy indique que la clé étrangère est gérée par Animal.species
     */
    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animal> animals;

    // ========== CONSTRUCTEURS ==========

    /**
     * Constructeur par défaut (requis par JPA)
     */
    public Species() {
    }

    /**
     * Constructeur avec paramètres (sans ID, auto-généré)
     * @param commonName Nom commun de l'espèce
     * @param latinName Nom scientifique en Latin
     */
    public Species(String commonName, String latinName) {
        this.commonName = commonName;
        this.latinName = latinName;
    }

    /**
     * Constructeur complet (avec ID)
     * @param id Identifiant unique
     * @param commonName Nom commun
     * @param latinName Nom scientifique
     */
    public Species(Integer id, String commonName, String latinName) {
        this.id = id;
        this.commonName = commonName;
        this.latinName = latinName;
    }

    // ========== GETTERS & SETTERS ==========

    /**
     * Récupère l'ID de l'espèce
     * @return ID de l'espèce
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit l'ID de l'espèce
     * @param id Nouvel ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Récupère le nom commun
     * @return Nom commun
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * Définit le nom commun
     * @param commonName Nouveau nom commun
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     * Récupère le nom scientifique
     * @return Nom scientifique en Latin
     */
    public String getLatinName() {
        return latinName;
    }

    /**
     * Définit le nom scientifique
     * @param latinName Nouveau nom scientifique
     */
    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    /**
     * Récupère la liste des animaux de cette espèce
     * @return Liste des animaux
     */
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * Définit la liste des animaux
     * @param animals Nouvelle liste d'animaux
     */
    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    // ========== MÉTHODES UTILITAIRES ==========

    /**
     * Affichage lisible de l'objet
     * @return Chaîne de caractères représentant Species
     */
    @Override
    public String toString() {
        return "Species{" +
                "id=" + id +
                ", commonName='" + commonName + '\'' +
                ", latinName='" + latinName + '\'' +
                '}';
    }

    /**
     * Égalité entre deux Species
     * @param obj Objet à comparer
     * @return true si égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Species species = (Species) obj;
        return id != null && id.equals(species.id);
    }

    /**
     * Code de hachage
     * @return Hash code
     */
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
