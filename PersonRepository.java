package com.example.demo.repository;

import com.example.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Person
 * Fournit les opérations CRUD de base et des requêtes personnalisées
 * 
 * Hérite de JpaRepository qui fournit:
 * - save(S entity): Insère ou met à jour une personne
 * - findAll(): Récupère toutes les personnes
 * - findById(ID id): Récupère une personne par son ID
 * - delete(S entity): Supprime une personne
 * - deleteById(ID id): Supprime une personne par son ID
 * - count(): Compte le nombre total de personnes
 * 
 * Méthodes personnalisées:
 * - findByLogin(String login): Recherche par login (identifiant unique)
 * - findByLastname(String lastname): Recherche par nom de famille
 * - findByActive(Boolean active): Récupère toutes les personnes actives/inactives
 * - findByFirstnameAndLastname(String firstname, String lastname): Recherche par prénom et nom
 * 
 * @author Moneli AGAMAKA IRA2026
 * @version 1.0
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    /**
     * Recherche une personne par son login (identifiant unique)
     * Très utile pour l'authentification
     * Exemple: findByLogin("hla")
     * 
     * @param login Login de la personne
     * @return Optional contenant la personne si trouvée, sinon Optional.empty()
     */
    Optional<Person> findByLogin(String login);

    /**
     * Récupère toutes les personnes ayant un nom de famille spécifique
     * Exemple: findByLastname("Lamarque")
     * 
     * @param lastname Nom de famille à rechercher
     * @return Liste de toutes les personnes avec ce nom
     */
    List<Person> findByLastname(String lastname);

    /**
     * Récupère toutes les personnes ayant un statut d'activation spécifique
     * Exemple: findByActive(true) pour les personnes actives
     * Exemple: findByActive(false) pour les personnes désactivées
     * 
     * @param active Statut d'activation (true = actif, false = inactif)
     * @return Liste des personnes avec ce statut
     */
    List<Person> findByActive(Boolean active);

    /**
     * Recherche une personne par prénom ET nom de famille
     * Plus précis que la recherche par nom seul
     * Exemple: findByFirstnameAndLastname("Henri", "Lamarque")
     * 
     * @param firstname Prénom
     * @param lastname Nom de famille
     * @return Optional contenant la personne si trouvée, sinon Optional.empty()
     */
    Optional<Person> findByFirstnameAndLastname(String firstname, String lastname);

    /**
     * Récupère toutes les personnes dont le prénom contient un texte
     * Recherche insensible à la casse
     * Exemple: findByFirstnameContainingIgnoreCase("jean") → retourne "Jean", "JEAN", etc.
     * 
     * @param firstname Texte à rechercher
     * @return Liste des personnes correspondantes
     */
    List<Person> findByFirstnameContainingIgnoreCase(String firstname);

    /**
     * Récupère toutes les personnes dont l'âge est supérieur ou égal
     * Utile pour les statistiques
     * Exemple: findByAgeGreaterThanEqual(18) pour les majeurs
     * 
     * @param age Âge minimum
     * @return Liste des personnes avec un âge >= au paramètre
     */
    List<Person> findByAgeGreaterThanEqual(Integer age);

    /**
     * Récupère toutes les personnes dont l'âge est inférieur ou égal
     * 
     * @param age Âge maximum
     * @return Liste des personnes avec un âge <= au paramètre
     */
    List<Person> findByAgeLessThanEqual(Integer age);

    /**
     * Compte le nombre de personnes actives
     * 
     * @return Nombre de personnes actives
     */
    long countByActiveTrue();

    /**
     * Compte le nombre de personnes inactives
     * 
     * @return Nombre de personnes inactives
     */
    long countByActiveFalse();
}
