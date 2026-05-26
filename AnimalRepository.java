package com.example.demo.repository;

import com.example.demo.model.Animal;
import com.example.demo.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Animal
 * Fournit les opérations CRUD de base et des requêtes personnalisées
 * 
 * Hérite de JpaRepository qui fournit:
 * - save(S entity): Insère ou met à jour une entité
 * - findAll(): Récupère tous les animaux
 * - findById(ID id): Récupère un animal par son ID
 * - delete(S entity): Supprime un animal
 * - deleteById(ID id): Supprime un animal par son ID
 * - count(): Compte le nombre total d'animaux
 * 
 * Méthodes personnalisées:
 * - findByName(String name): Recherche par nom d'animal
 * - findBySpecies(Species species): Récupère tous les animaux d'une espèce
 * - findByColor(String color): Recherche par couleur
 * - findBySex(String sex): Récupère tous les animaux d'un sexe donné
 * 
 * @author Moneli AGAMAKA IRA2026
 * @version 1.0
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    /**
     * Recherche un animal par son nom
     * Exemple: findByName("Max")
     * 
     * @param name Nom de l'animal
     * @return Optional contenant l'animal si trouvé, sinon Optional.empty()
     */
    Optional<Animal> findByName(String name);

    /**
     * Récupère tous les animaux d'une espèce donnée
     * Exemple: findBySpecies(chatSpecies)
     * 
     * @param species Espèce recherchée
     * @return Liste de tous les animaux de cette espèce
     */
    List<Animal> findBySpecies(Species species);

    /**
     * Récupère tous les animaux d'une couleur donnée
     * Exemple: findByColor("Noir")
     * 
     * @param color Couleur recherchée
     * @return Liste de tous les animaux de cette couleur
     */
    List<Animal> findByColor(String color);

    /**
     * Récupère tous les animaux d'un sexe donné
     * Exemple: findBySex("M") pour les mâles
     * Exemple: findBySex("F") pour les femelles
     * 
     * @param sex Sexe recherché (M ou F)
     * @return Liste de tous les animaux de ce sexe
     */
    List<Animal> findBySex(String sex);

    /**
     * Récupère tous les animaux dont le nom contient un texte
     * Recherche insensible à la casse
     * Exemple: findByNameContainingIgnoreCase("max") → retourne "Max", "MAX", etc.
     * 
     * @param name Texte à rechercher
     * @return Liste des animaux correspondants
     */
    List<Animal> findByNameContainingIgnoreCase(String name);

    /**
     * Compte le nombre d'animaux d'une espèce donnée
     * 
     * @param species Espèce recherchée
     * @return Nombre d'animaux de cette espèce
     */
    long countBySpecies(Species species);
}
