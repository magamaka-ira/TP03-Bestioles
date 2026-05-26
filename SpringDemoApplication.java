package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

/**
 * Classe principale Spring Boot - TP03 Bestioles
 * Application de gestion d'animaux de compagnie avec repositories JPA
 * 
 * Cette classe implémente CommandLineRunner pour exécuter du code au démarrage
 * Permet de tester les repositories sans créer une REST API
 * 
 * Structure du projet:
 * ├── model/
 * │   ├── Species.java
 * │   ├── Animal.java
 * │   ├── Person.java
 * │   └── Role.java
 * ├── repository/
 * │   ├── SpeciesRepository.java
 * │   ├── AnimalRepository.java
 * │   ├── PersonRepository.java
 * │   └── RoleRepository.java
 * └── SpringDemoApplication.java (cette classe)
 * 
 * @author Moneli AGAMAKA IRA2026
 * @version 1.0
 * @date Décembre 2024
 */
@SpringBootApplication
public class SpringDemoApplication implements CommandLineRunner {

    // ========== INJECTION DES REPOSITORIES ==========

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Point d'entrée de l'application
     * 
     * @param args Arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringDemoApplication.class, args);
    }

    /**
     * Méthode run() appelée après le démarrage de Spring
     * Exécute les tests des repositories
     * 
     * @param args Arguments de ligne de commande
     * @throws Exception Exception potentielle
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TP03 - SPRING DATA JPA & REPOSITORIES - TESTS");
        System.out.println("Étudiant: Moneli AGAMAKA IRA2026");
        System.out.println("Date: Décembre 2024");
        System.out.println("=".repeat(80) + "\n");

        // Tester les repositories
        testSpeciesRepository();
        testAnimalRepository();
        testPersonRepository();
        testRoleRepository();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("TESTS TERMINÉS AVEC SUCCÈS ✅");
        System.out.println("=".repeat(80) + "\n");
    }

    // ========== TESTS DES REPOSITORIES ==========

    /**
     * Tests du SpeciesRepository
     * Teste: findAll(), findById(), findByCommonName()
     */
    private void testSpeciesRepository() {
        System.out.println("\n📌 TEST 1: SpeciesRepository");
        System.out.println("-".repeat(80));

        // findAll() - Afficher toutes les espèces
        System.out.println("\n1️⃣ Récupérer toutes les espèces (findAll):");
        List<Species> allSpecies = speciesRepository.findAll();
        allSpecies.forEach(species -> 
            System.out.println("   ✓ " + species.getCommonName() + " (" + species.getLatinName() + ")")
        );
        System.out.println("   Total: " + allSpecies.size() + " espèces");

        // findById() - Rechercher une espèce par ID
        System.out.println("\n2️⃣ Récupérer une espèce par ID (findById):");
        Optional<Species> species = speciesRepository.findById(1);
        species.ifPresent(s -> 
            System.out.println("   ✓ Espèce trouvée: " + s.getCommonName())
        );

        // findByCommonName() - Rechercher par nom commun
        System.out.println("\n3️⃣ Rechercher une espèce par nom commun (findByCommonName):");
        Optional<Species> chat = speciesRepository.findByCommonName("Chat");
        chat.ifPresent(s -> 
            System.out.println("   ✓ Trouvée: " + s.getCommonName() + " - " + s.getLatinName())
        );

        // count() - Compter le nombre d'espèces
        System.out.println("\n4️⃣ Compter le nombre total d'espèces (count):");
        long speciesCount = speciesRepository.count();
        System.out.println("   ✓ Nombre d'espèces: " + speciesCount);
    }

    /**
     * Tests du AnimalRepository
     * Teste: findAll(), findById(), findBySpecies(), findBySex()
     */
    private void testAnimalRepository() {
        System.out.println("\n\n📌 TEST 2: AnimalRepository");
        System.out.println("-".repeat(80));

        // findAll() - Afficher tous les animaux
        System.out.println("\n1️⃣ Récupérer tous les animaux (findAll):");
        List<Animal> allAnimals = animalRepository.findAll();
        allAnimals.forEach(animal -> 
            System.out.println("   ✓ " + animal.getName() + " - " + animal.getSpecies().getCommonName() + " (" + animal.getColor() + ")")
        );
        System.out.println("   Total: " + allAnimals.size() + " animaux");

        // findById() - Rechercher un animal par ID
        System.out.println("\n2️⃣ Récupérer un animal par ID (findById):");
        Optional<Animal> animal = animalRepository.findById(1);
        animal.ifPresent(a -> 
            System.out.println("   ✓ Animal trouvé: " + a.getName())
        );

        // findBySpecies() - Tous les animaux d'une espèce
        System.out.println("\n3️⃣ Récupérer tous les animaux d'une espèce (findBySpecies):");
        Optional<Species> chat = speciesRepository.findByCommonName("Chat");
        if (chat.isPresent()) {
            List<Animal> cats = animalRepository.findBySpecies(chat.get());
            cats.forEach(a -> 
                System.out.println("   ✓ " + a.getName() + " (" + a.getColor() + ")")
            );
            System.out.println("   Total chats: " + cats.size());
        }

        // findBySex() - Tous les animaux d'un sexe donné
        System.out.println("\n4️⃣ Récupérer tous les animaux mâles (findBySex):");
        List<Animal> males = animalRepository.findBySex("M");
        males.forEach(a -> 
            System.out.println("   ✓ " + a.getName() + " - " + a.getSpecies().getCommonName())
        );
        System.out.println("   Total mâles: " + males.size());

        // count() - Compter le nombre d'animaux
        System.out.println("\n5️⃣ Compter le nombre total d'animaux (count):");
        long animalCount = animalRepository.count();
        System.out.println("   ✓ Nombre d'animaux: " + animalCount);
    }

    /**
     * Tests du PersonRepository
     * Teste: findAll(), findById(), findByLogin(), findByActive()
     */
    private void testPersonRepository() {
        System.out.println("\n\n📌 TEST 3: PersonRepository");
        System.out.println("-".repeat(80));

        // findAll() - Afficher toutes les personnes
        System.out.println("\n1️⃣ Récupérer toutes les personnes (findAll):");
        List<Person> allPersons = personRepository.findAll();
        allPersons.forEach(person -> 
            System.out.println("   ✓ " + person.getFullName() + " (Login: " + person.getLogin() + ", Âge: " + person.getAge() + ")")
        );
        System.out.println("   Total: " + allPersons.size() + " personnes");

        // findById() - Rechercher une personne par ID
        System.out.println("\n2️⃣ Récupérer une personne par ID (findById):");
        Optional<Person> person = personRepository.findById(1);
        person.ifPresent(p -> 
            System.out.println("   ✓ Personne trouvée: " + p.getFullName())
        );

        // findByLogin() - Rechercher par login
        System.out.println("\n3️⃣ Rechercher une personne par login (findByLogin):");
        Optional<Person> henri = personRepository.findByLogin("hla");
        henri.ifPresent(p -> 
            System.out.println("   ✓ Trouvée: " + p.getFullName() + " (" + p.getAge() + " ans)")
        );

        // findByActive() - Toutes les personnes actives
        System.out.println("\n4️⃣ Récupérer toutes les personnes actives (findByActive):");
        List<Person> activePersons = personRepository.findByActive(true);
        activePersons.forEach(p -> 
            System.out.println("   ✓ " + p.getFullName())
        );
        System.out.println("   Total actifs: " + activePersons.size());

        // count() - Compter le nombre de personnes
        System.out.println("\n5️⃣ Compter le nombre total de personnes (count):");
        long personCount = personRepository.count();
        System.out.println("   ✓ Nombre de personnes: " + personCount);

        // countByActiveTrue() - Compter les personnes actives
        System.out.println("\n6️⃣ Compter les personnes actives (countByActiveTrue):");
        long activeCount = personRepository.countByActiveTrue();
        System.out.println("   ✓ Personnes actives: " + activeCount);
    }

    /**
     * Tests du RoleRepository
     * Teste: findAll(), findById(), findByLabel()
     */
    private void testRoleRepository() {
        System.out.println("\n\n📌 TEST 4: RoleRepository");
        System.out.println("-".repeat(80));

        // findAll() - Afficher tous les rôles
        System.out.println("\n1️⃣ Récupérer tous les rôles (findAll):");
        List<Role> allRoles = roleRepository.findAll();
        allRoles.forEach(role -> 
            System.out.println("   ✓ " + role.getLabel())
        );
        System.out.println("   Total: " + allRoles.size() + " rôles");

        // findById() - Rechercher un rôle par ID
        System.out.println("\n2️⃣ Récupérer un rôle par ID (findById):");
        Optional<Role> role = roleRepository.findById(1);
        role.ifPresent(r -> 
            System.out.println("   ✓ Rôle trouvé: " + r.getLabel())
        );

        // findByLabel() - Rechercher par label
        System.out.println("\n3️⃣ Rechercher un rôle par label (findByLabel):");
        Optional<Role> adminRole = roleRepository.findByLabel("ROLE_ADMIN");
        adminRole.ifPresent(r -> 
            System.out.println("   ✓ Trouvé: " + r.getLabel())
        );

        // count() - Compter le nombre de rôles
        System.out.println("\n4️⃣ Compter le nombre total de rôles (count):");
        long roleCount = roleRepository.count();
        System.out.println("   ✓ Nombre de rôles: " + roleCount);
    }
}
