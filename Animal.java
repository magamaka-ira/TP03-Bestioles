package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entité Animal
 * Représente un animal dans le système
 * 
 * Table: animal
 * Colonnes:
 *   - id (PK): Identifiant unique
 *   - name: Nom de l'animal
 *   - color: Couleur de l'animal
 *   - sex: Sexe de l'animal (M ou F)
 *   - species_id (FK): Référence vers Species
 * 
 * Relations:
 *   - ManyToOne avec Species
 *   - ManyToMany avec Person (via person_animals)
 * 
 * @author Moneli AGAMAKA IRA2026
 * @version 1.0
 */
@Entity
@Table(name = "animal")
public class Animal {

    /**
     * Identifiant unique de l'animal
     * Type: INT, AutoIncrement, Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom de l'animal
     * Type: VARCHAR(50), NOT NULL
     * Exemples: Max, Médor, Loulou, Minouchette
     */
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * Couleur de l'animal
     * Type: VARCHAR(50), NULLABLE
     * Exemples: Gris tacheté, Blanc, Noir, Brun, Roux
     */
    @Column(name = "color", length = 50)
    private String color;

    /**
     * Sexe de l'animal
     * Type: VARCHAR(255), NOT NULL
     * Valeurs: M (mâle) ou F (femelle)
     */
    @Column(name = "sex", nullable = false)
    private String sex;

    /**
     * Relation ManyToOne vers Species
     * Plusieurs animaux peuvent appartenir à la même espèce
     * La clé étrangère est species_id
     */
    @ManyToOne
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;

    /**
     * Relation ManyToMany vers Person
     * Un animal peut appartenir à plusieurs propriétaires
     * Un propriétaire peut avoir plusieurs animaux
     * La table de liaison est person_animals
     */
    @ManyToMany(mappedBy = "animals", fetch = FetchType.LAZY)
    private List<Person> persons;

    // ========== CONSTRUCTEURS ==========

    /**
     * Constructeur par défaut (requis par JPA)
     */
    public Animal() {
    }

    /**
     * Constructeur avec paramètres principaux
     * @param name Nom de l'animal
     * @param color Couleur de l'animal
     * @param sex Sexe de l'animal (M ou F)
     * @param species Espèce de l'animal
     */
    public Animal(String name, String color, String sex, Species species) {
        this.name = name;
        this.color = color;
        this.sex = sex;
        this.species = species;
    }

    // ========== GETTERS & SETTERS ==========

    /**
     * Récupère l'ID de l'animal
     * @return ID de l'animal
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit l'ID de l'animal
     * @param id Nouvel ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Récupère le nom de l'animal
     * @return Nom de l'animal
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'animal
     * @param name Nouveau nom
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Récupère la couleur de l'animal
     * @return Couleur de l'animal
     */
    public String getColor() {
        return color;
    }

    /**
     * Définit la couleur de l'animal
     * @param color Nouvelle couleur
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Récupère le sexe de l'animal
     * @return Sexe (M ou F)
     */
    public String getSex() {
        return sex;
    }

    /**
     * Définit le sexe de l'animal
     * @param sex Nouveau sexe
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Récupère l'espèce de l'animal
     * @return Espèce de l'animal
     */
    public Species getSpecies() {
        return species;
    }

    /**
     * Définit l'espèce de l'animal
     * @param species Nouvelle espèce
     */
    public void setSpecies(Species species) {
        this.species = species;
    }

    /**
     * Récupère la liste des propriétaires
     * @return Liste des propriétaires
     */
    public List<Person> getPersons() {
        return persons;
    }

    /**
     * Définit la liste des propriétaires
     * @param persons Nouvelle liste de propriétaires
     */
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    // ========== MÉTHODES UTILITAIRES ==========

    /**
     * Affichage lisible de l'objet
     * @return Chaîne de caractères représentant Animal
     */
    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", sex='" + sex + '\'' +
                ", species=" + (species != null ? species.getCommonName() : "null") +
                '}';
    }

    /**
     * Égalité entre deux Animal
     * @param obj Objet à comparer
     * @return true si égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal animal = (Animal) obj;
        return id != null && id.equals(animal.id);
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
