package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entité Role
 * Représente un rôle utilisateur (ROLE_USER, ROLE_ADMIN, ROLE_EMPLOYE)
 * 
 * Table: role
 * Colonnes:
 *   - id (PK): Identifiant unique
 *   - label: Nom du rôle
 * 
 * Relations:
 *   - ManyToMany avec Person (via person_role)
 * 
 * @author Moneli AGAMAKA IRA2026
 * @version 1.0
 */
@Entity
@Table(name = "role")
public class Role {

    /**
     * Identifiant unique du rôle
     * Type: INT UNSIGNED, AutoIncrement, Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Label/Nom du rôle
     * Type: VARCHAR(50), NOT NULL
     * Exemples: ROLE_USER, ROLE_ADMIN, ROLE_EMPLOYE
     */
    @Column(name = "label", nullable = false, length = 50)
    private String label;

    /**
     * Relation ManyToMany vers Person
     * Un rôle peut être assigné à plusieurs personnes
     * Une personne peut avoir plusieurs rôles
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Person> persons;

    // ========== CONSTRUCTEURS ==========

    /**
     * Constructeur par défaut (requis par JPA)
     */
    public Role() {
    }

    /**
     * Constructeur avec paramètres (sans ID)
     * @param label Nom du rôle
     */
    public Role(String label) {
        this.label = label;
    }

    /**
     * Constructeur complet (avec ID)
     * @param id Identifiant unique
     * @param label Nom du rôle
     */
    public Role(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    // ========== GETTERS & SETTERS ==========

    /**
     * Récupère l'ID du rôle
     * @return ID du rôle
     */
    public Integer getId() {
        return id;
    }

    /**
     * Définit l'ID du rôle
     * @param id Nouvel ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Récupère le label du rôle
     * @return Label/Nom du rôle
     */
    public String getLabel() {
        return label;
    }

    /**
     * Définit le label du rôle
     * @param label Nouveau label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Récupère la liste des personnes ayant ce rôle
     * @return Liste des personnes
     */
    public List<Person> getPersons() {
        return persons;
    }

    /**
     * Définit la liste des personnes
     * @param persons Nouvelle liste
     */
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    // ========== MÉTHODES UTILITAIRES ==========

    /**
     * Affichage lisible de l'objet
     * @return Chaîne de caractères représentant Role
     */
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }

    /**
     * Égalité entre deux Role
     * @param obj Objet à comparer
     * @return true si égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role) obj;
        return id != null && id.equals(role.id);
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
