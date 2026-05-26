package com.example.demo.repository;

import com.example.demo.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Species
 * Fournit les opérations CRUD de base et des requêtes personnalisées
 * 
 * Hérite de JpaRepository qui fournit:
 * - save(S entity): Insère ou met à jour une entité
 * - findAll(): Récupère toutes les entités
 * - findById(ID id): Récupère une entité par son ID
 * - delete(S entity): Supprime une entité
 * - deleteById(ID id): Supprime une entité par son ID
 * - count(): Compte le nombre total d'entités
 * 
 * Méthodes personnalisées:
 * - findByCommonName(String commonName): Recherche par nom commun
 * - findByLatinName(String latinName): Recherche par nom Latin
 * 
 * @author Moneli AGAMAKA IRA2026
 * @version 1.0
 */
@Repository
public interface SpeciesRepository extends JpaRepository<Species, Integer> {

    /**
     * Recherche une espèce par son nom commun
     * Exemple: findByCommonName("Chat")
     * 
     * @param commonName Nom commun de l'espèce
     * @return Optional contenant l'espèce si trouvée, sinon Optional.empty()
     */
    Optional<Species> findByCommonName(String commonName);

    /**
     * Recherche une espèce par son nom scientifique (Latin)
     * Exemple: findByLatinName("Felis silvestris catus")
     * 
     * @param latinName Nom scientifique en Latin
     * @return Optional contenant l'espèce si trouvée, sinon Optional.empty()
     */
    Optional<Species> findByLatinName(String latinName);

    /**
     * Recherche toutes les espèces dont le nom commun contient un texte
     * Recherche insensible à la casse grâce à LIKE
     * Exemple: findByCommonNameContainingIgnoreCase("chat") → retourne "Chat", "CHAT", etc.
     * 
     * @param commonName Texte à rechercher
     * @return Liste des espèces correspondantes
     */
    List<Species> findByCommonNameContainingIgnoreCase(String commonName);
}
