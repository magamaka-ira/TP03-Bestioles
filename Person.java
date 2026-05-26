package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entité Person
 * Représente une personne (utilisateur) du système
 * 
 * Table: person
 * Colonnes:
 *   - id (PK): Identifiant unique
 *   - firstname: Prénom
 *   - lastname: Nom de famille
 *   - age: Âge de la personne
 *   - login: Identifiant unique pour la connexion
 *   - mdp: Mot de passe (hashé en production)
 *   - active: Statut d'activation (1 = actif, 0 = inactif)
 * 
 * Relations:
 *   - ManyToMany avec Animal (via person_animals)
 *   - ManyToMany avec Role (via person_role)
 * 
 * @author Moneli AGAMAKA IRA2026
 * @version 1.0
 */
@Entity
@Table(name = "person")
public class Person {

    /**
     * Identifiant unique de la personne
     * Type: INT, AutoIncrement, Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Prénom de la personne
     * Type: VARCHAR(50), NOT NULL
     */
    @Column(name = "firstname", nullable = false, length = 50)
    private String firstname;

    /**
     * Nom de famille de la personne
     * Type: VARCHAR(50), NOT NULL
     */
    @Column(name = "lastname", nullable = false, length = 50)
    private String lastname;

    /**
     * Âge de la personne
     * Type: INT, NULLABLE
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Login de la personne (identifiant unique pour connexion)
     * Type: VARCHAR(50), NOT NULL, UNIQUE
     */
    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String login;

    /**
     * Mot de passe de la personne
     * Type: VARCHAR(100), NOT NULL
     * Note: En production, doit être hashé avec BCrypt ou Argon2
     */
    @Column(name = "mdp", nullable = false, length = 100)
    private String mdp;

    /**
     * Statut d'activation de la personne
     * Type: TINYINT(4), DEFAULT 1
     * Valeurs: 1 = actif, 0 = inactif
     */
    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(4) DEFAULT 1")
    private Boolean active = true;

    /**
     * Relation ManyToMany vers Animal
     * Une personne peut avoir plusieurs animaux
     * Un animal peut appartenir à plusieurs personnes
     * La table de liaison est person_animals
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_animals",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "animals_id")
    )
    private List<Animal> animals;

    /**
     * Relation ManyToMany vers Role
     * Une personne peut avoir plusieurs rôles
     * Un rôle peut être assigné à plusieurs personnes
     * La table de liaison est person_role
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    // ========== CONSTRUCTEURS ==========

    /**
     * Constructeur par défaut (requis par JPA)
     */
    public Person() {
    }

    /**
     * Constructeur avec paramètres principaux
     * @param firstname Prénom
     * @param lastname Nom de famille
     * @param age Âge
     * @param login Login (identifiant)
     * @param mdp Mot de passe
     */
    public Person(String firstname, String lastname, Integer age, String login, String mdp) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.login = login;
        this.mdp = mdp;
        this.active = true;
    }

    // ========== GETTERS & SETTERS ==========

    /**
     * Récupère l'ID de la personne
     * @return ID de la personne
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit l'ID de la personne
     * @param id Nouvel ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Récupère le prénom
     * @return Prénom
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Définit le prénom
     * @param firstname Nouveau prénom
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Récupère le nom de famille
     * @return Nom de famille
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Définit le nom de famille
     * @param lastname Nouveau nom
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Récupère l'âge
     * @return Âge
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Définit l'âge
     * @param age Nouvel âge
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Récupère le login
     * @return Login (identifiant)
     */
    public String getLogin() {
        return login;
    }

    /**
     * Définit le login
     * @param login Nouveau login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Récupère le mot de passe
     * @return Mot de passe
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Définit le mot de passe
     * @param mdp Nouveau mot de passe
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Récupère le statut d'activation
     * @return true si actif, false sinon
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Définit le statut d'activation
     * @param active Nouveau statut
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Récupère la liste des animaux
     * @return Liste des animaux
     */
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * Définit la liste des animaux
     * @param animals Nouvelle liste
     */
    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    /**
     * Récupère la liste des rôles
     * @return Liste des rôles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Définit la liste des rôles
     * @param roles Nouvelle liste
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    // ========== MÉTHODES UTILITAIRES ==========

    /**
     * Récupère le nom complet (Prénom Nom)
     * @return Nom complet
     */
    public String getFullName() {
        return firstname + " " + lastname;
    }

    /**
     * Affichage lisible de l'objet
     * @return Chaîne de caractères représentant Person
     */
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", login='" + login + '\'' +
                ", active=" + active +
                '}';
    }

    /**
     * Égalité entre deux Person
     * @param obj Objet à comparer
     * @return true si égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return id != null && id.equals(person.id);
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
