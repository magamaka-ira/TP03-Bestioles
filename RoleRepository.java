package com.example.demo.repository;

import com.example.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository pour l'entité Role
 * Fournit les opérations CRUD de base et des requêtes personnalisées
 * 
 * Hérite de JpaRepository qui fournit:
 * - save(S entity): Insère ou met à jour un rôle
 * - findAll(): Récupère tous les rôles
 * - findById(ID id): Récupère un rôle par son ID
 * - delete(S entity): Supprime un rôle
 * - deleteById(ID id): Supprime un rôle par son ID
 * - count(): Compte le nombre total de rôles
 * 
 * Méthodes personnalisées:
 * - findByLabel(String label): Recherche un rôle par son label/nom
 * 
 * @author Moneli AGAMAKA IRA2026
 * @version 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * Recherche un rôle par son label/nom
     * Exemple: findByLabel("ROLE_ADMIN")
     * 
     * @param label Label/Nom du rôle
     * @return Optional contenant le rôle si trouvé, sinon Optional.empty()
     */
    Optional<Role> findByLabel(String label);
}
