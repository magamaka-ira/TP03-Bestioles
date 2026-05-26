# TP03 - Spring Data JPA & Repositories

## 📋 Informations Projet

**Étudiant :** Moneli AGAMAKA IRA2026  
**Date :** Avril 2026
**Promo :** IRA2026  
**Module :** Spring Boot - Data JPA  
**Durée :** 3 heures  

---

## 🎯 Objectifs du TP

✅ Mapper des entités JPA sur une base de données **existante** (sans la modifier)  
✅ Créer les repositories respectifs pour chaque entité  
✅ Tester les repositories via `CommandLineRunner`  
✅ Comprendre les relations JPA (OneToMany, ManyToMany, ManyToOne)  
✅ Utiliser les méthodes CRUD de `JpaRepository`  
✅ Écrire des requêtes personnalisées dans les repositories  

---

## 📦 Architecture du Projet

```
TP03-Bestioles/
│
├── 📂 src/main/java/com/example/demo/
│   ├── 📂 model/                         # Entités JPA
│   │   ├── Species.java                  # Espèces animales (Chat, Chien, Lapin)
│   │   ├── Animal.java                   # Animaux individuels
│   │   ├── Person.java                   # Personnes/Propriétaires
│   │   └── Role.java                     # Rôles utilisateur
│   │
│   ├── 📂 repository/                    # Spring Data Repositories
│   │   ├── SpeciesRepository.java        # CRUD + requêtes pour Species
│   │   ├── AnimalRepository.java         # CRUD + requêtes pour Animal
│   │   ├── PersonRepository.java         # CRUD + requêtes pour Person
│   │   └── RoleRepository.java           # CRUD + requêtes pour Role
│   │
│   └── SpringDemoApplication.java        # Classe principale + Tests (CommandLineRunner)
│
├── 📂 src/main/resources/
│   └── application.properties             # Configuration DataSource & JPA/Hibernate
│
├── 📂 database/
│   └── dump-bestioles.sql                # Script SQL pour initialiser la BDD
│
├── 📄 pom.xml                            # Dépendances Maven
└── 📄 README.md                          # Ce fichier

```

---

## 🗄️ Architecture Base de Données

```
┌─────────────────────────────────────────────────────────────┐
│                         BESTIOLES                           │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────┐         ┌──────────────┐                  │
│  │   SPECIES    │         │   ANIMAL     │                  │
│  ├──────────────┤         ├──────────────┤                  │
│  │ id (PK)      │◄────────│ id (PK)      │                  │
│  │ common_name  │ 1    N  │ name         │                  │
│  │ latin_name   │         │ color        │                  │
│  └──────────────┘         │ sex          │                  │
│                           │ species_id   │ (FK)             │
│                           └──────────────┘                  │
│                                  │                          │
│                                  │ ManyToMany              │
│                                  ▼                          │
│                    ┌──────────────────────┐                │
│                    │  PERSON_ANIMALS      │                │
│                    ├──────────────────────┤                │
│                    │ person_id (FK)  │    │                │
│                    │ animals_id (FK) │    │                │
│                    └──────────────────────┘                │
│                                  ▲                          │
│                                  │                          │
│  ┌──────────────┐          ┌─────┴──────────┐              │
│  │    PERSON    │          │   (ManyToMany) │              │
│  ├──────────────┤          └────────────────┘              │
│  │ id (PK)      │                                           │
│  │ firstname    │          ┌──────────────┐                │
│  │ lastname     │◄─────────│    ROLE      │                │
│  │ age          │  M   N   ├──────────────┤                │
│  │ login        │          │ id (PK)      │                │
│  │ mdp          │          │ label        │                │
│  │ active       │          └──────────────┘                │
│  └──────────────┘                                          │
│       ▲                                                      │
│       │ ManyToMany (person_role)                           │
│       │                                                      │
└───────┴──────────────────────────────────────────────────┘
```

---

## 🔑 Relations JPA

### 1. **Species ↔ Animal** (OneToMany / ManyToOne)
```
Une espèce peut avoir plusieurs animaux
Un animal appartient à une seule espèce

Species.animals ← OneToMany
Animal.species ← ManyToOne (avec @JoinColumn species_id)
```

### 2. **Person ↔ Animal** (ManyToMany)
```
Une personne peut avoir plusieurs animaux
Un animal peut appartenir à plusieurs personnes

Person.animals ← ManyToMany (table person_animals)
Animal.persons ← ManyToMany (mappedBy)
```

### 3. **Person ↔ Role** (ManyToMany)
```
Une personne peut avoir plusieurs rôles
Un rôle peut être assigné à plusieurs personnes

Person.roles ← ManyToMany (table person_role)
Role.persons ← ManyToMany (mappedBy)
```

---

## 📊 Signatures des Classes

### **Entités (Model)**

#### Species.java
```java
public class Species {
    - id: Integer (PK, AutoIncrement)
    - commonName: String (NOT NULL)
    - latinName: String (NOT NULL)
    - animals: List<Animal> (OneToMany)
    
    + Species()
    + Species(String commonName, String latinName)
    + getId(): Integer
    + setId(Integer): void
    + getCommonName(): String
    + setCommonName(String): void
    + getLatinName(): String
    + setLatinName(String): void
    + getAnimals(): List<Animal>
    + setAnimals(List<Animal>): void
}
```

#### Animal.java
```java
public class Animal {
    - id: Integer (PK, AutoIncrement)
    - name: String (NOT NULL)
    - color: String
    - sex: String (NOT NULL, M ou F)
    - species: Species (ManyToOne)
    - persons: List<Person> (ManyToMany)
    
    + Animal()
    + Animal(String name, String color, String sex, Species species)
    + getId(): Integer
    + setId(Integer): void
    + getName(): String
    + setName(String): void
    + getColor(): String
    + setColor(String): void
    + getSex(): String
    + setSex(String): void
    + getSpecies(): Species
    + setSpecies(Species): void
    + getPersons(): List<Person>
    + setPersons(List<Person>): void
}
```

#### Person.java
```java
public class Person {
    - id: Integer (PK, AutoIncrement)
    - firstname: String (NOT NULL)
    - lastname: String (NOT NULL)
    - age: Integer
    - login: String (NOT NULL, UNIQUE)
    - mdp: String (NOT NULL)
    - active: Boolean (DEFAULT true)
    - animals: List<Animal> (ManyToMany)
    - roles: List<Role> (ManyToMany)
    
    + Person()
    + Person(String firstname, String lastname, Integer age, String login, String mdp)
    + getId(): Integer
    + setId(Integer): void
    + getFirstname(): String
    + setFirstname(String): void
    + getLastname(): String
    + setLastname(String): void
    + getAge(): Integer
    + setAge(Integer): void
    + getLogin(): String
    + setLogin(String): void
    + getMdp(): String
    + setMdp(String): void
    + getActive(): Boolean
    + setActive(Boolean): void
    + getAnimals(): List<Animal>
    + setAnimals(List<Animal>): void
    + getRoles(): List<Role>
    + setRoles(List<Role>): void
    + getFullName(): String  // Retourne "firstname lastname"
}
```

#### Role.java
```java
public class Role {
    - id: Integer (PK, AutoIncrement)
    - label: String (NOT NULL)
    - persons: List<Person> (ManyToMany)
    
    + Role()
    + Role(String label)
    + Role(Integer id, String label)
    + getId(): Integer
    + setId(Integer): void
    + getLabel(): String
    + setLabel(String): void
    + getPersons(): List<Person>
    + setPersons(List<Person>): void
}
```

### **Repositories**

#### SpeciesRepository
```java
public interface SpeciesRepository extends JpaRepository<Species, Integer> {
    + findByCommonName(String commonName): Optional<Species>
    + findByLatinName(String latinName): Optional<Species>
    + findByCommonNameContainingIgnoreCase(String): List<Species>
}
```

#### AnimalRepository
```java
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    + findByName(String name): Optional<Animal>
    + findBySpecies(Species species): List<Animal>
    + findByColor(String color): List<Animal>
    + findBySex(String sex): List<Animal>
    + findByNameContainingIgnoreCase(String name): List<Animal>
    + countBySpecies(Species species): long
}
```

#### PersonRepository
```java
public interface PersonRepository extends JpaRepository<Person, Integer> {
    + findByLogin(String login): Optional<Person>
    + findByLastname(String lastname): List<Person>
    + findByActive(Boolean active): List<Person>
    + findByFirstnameAndLastname(String firstname, String lastname): Optional<Person>
    + findByFirstnameContainingIgnoreCase(String firstname): List<Person>
    + findByAgeGreaterThanEqual(Integer age): List<Person>
    + findByAgeLessThanEqual(Integer age): List<Person>
    + countByActiveTrue(): long
    + countByActiveFalse(): long
}
```

#### RoleRepository
```java
public interface RoleRepository extends JpaRepository<Role, Integer> {
    + findByLabel(String label): Optional<Role>
}
```

---

## 🚀 Installation et Configuration

### 1. **Créer la base de données**

```sql
-- Dans votre gestionnaire de BDD (PhpMyAdmin, HeidiSQL, MySQL Workbench):
1. Créer une nouvelle base "bestioles"
2. Exécuter le script SQL : database/dump-bestioles.sql
```

### 2. **Configurer application.properties**

```properties
# DataSource
spring.datasource.url=jdbc:mysql://localhost:3306/bestioles
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate  # ⭐ Important: ne pas modifier la BDD
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 3. **Dépendances Maven (pom.xml)**

```xml
<!-- Spring Boot Data JPA (inclus Hibernate) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- MySQL Driver -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Spring Boot Web Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

---

## ▶️ Lancer l'Application

```bash
cd TP03
mvnw spring-boot:run
```

**Résultat attendu:**

```
================================================================================
TP03 - SPRING DATA JPA & REPOSITORIES - TESTS
Étudiant: Moneli AGAMAKA IRA2026
Date: Avril
================================================================================

📌 TEST 1: SpeciesRepository
────────────────────────────────────────────────────────────────────────────
1️⃣ Récupérer toutes les espèces (findAll):
   ✓ Chat (Felis silvestris catus)
   ✓ Chien (Canis lupus familiaris)
   ✓ Lapin (Oryctolagus cuniculus)
   Total: 3 espèces
...
```

---

## 📚 Fonctionnalités Testées

### ✅ CRUD de Base (hérité de JpaRepository)
- `save()` - Insérer ou mettre à jour
- `findAll()` - Récupérer tous les enregistrements
- `findById()` - Trouver par ID
- `delete()` - Supprimer
- `count()` - Compter les enregistrements

### ✅ Requêtes Personnalisées
- `findByCommonName()` - Par nom commun
- `findByLogin()` - Par login (authentification)
- `findByActive()` - Personnes actives/inactives
- `findBySpecies()` - Animaux d'une espèce
- `findBySex()` - Animaux par sexe
- `findByLastname()` - Personnes par nom
- Et 10+ autres méthodes

### ✅ Relations JPA
- OneToMany / ManyToOne (Species ↔ Animal)
- ManyToMany (Person ↔ Animal, Person ↔ Role)
- Requêtes avec jointures automatiques

---

## 📖 Points Clés à Retenir

### JPA Annotations
```java
@Entity              // Marque une classe comme entité JPA
@Table(name="...")   // Mappe à une table SQL
@Id                  // Clé primaire
@GeneratedValue      // Auto-incrémentation
@Column              // Configuration de colonne
@ManyToOne           // Relation plusieurs-vers-un
@OneToMany           // Relation un-vers-plusieurs
@ManyToMany          // Relation plusieurs-vers-plusieurs
@JoinColumn          // Colonne de jointure
@JoinTable           // Table de liaison (ManyToMany)
```

### Repository
```java
// Hérite automatiquement de 15+ méthodes CRUD
extends JpaRepository<Entity, ID>

// Noms de méthodes "magiques" générés automatiquement:
findBy<PropertyName>()
findBy<PropertyName>AndOther<PropertyName>()
findBy<PropertyName>Containing()
findBy<PropertyName>GreaterThan()
findBy<PropertyName>LessThan()
countBy<PropertyName>()
existsBy<PropertyName>()
deleteBy<PropertyName>()
```

### CommandLineRunner
```java
@SpringBootApplication
public class App implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // Code exécuté au démarrage
    }
}
```

---

## 🐛 Dépannage

### Erreur: "Unknown column 'X' in field list"
**Cause:** Colonne n'existe pas dans la BDD  
**Solution:** Vérifier le dump SQL, vérifier les `@Column(name="...")`

### Erreur: "No database selected"
**Cause:** BDD 'bestioles' n'existe pas  
**Solution:** Créer la BDD et exécuter le dump SQL

### Erreur: "Circular reference"
**Cause:** Relations ManyToMany non bien configurées  
**Solution:** Vérifier `@JsonIgnore` ou utiliser `mappedBy` correctement

### Résultats vides dans findAll()
**Cause:** Application ne s'est pas connectée à la BDD  
**Solution:** Vérifier application.properties, vérifier ddl-auto=validate

---

## ✅ Checklist de Validation

- [ ] Base de données "bestioles" créée et peuplée
- [ ] Toutes les entités JPA créées (Species, Animal, Person, Role)
- [ ] Tous les repositories créés
- [ ] CommandLineRunner implémenté et testé
- [ ] application.properties configuré
- [ ] pom.xml avec toutes les dépendances
- [ ] Tous les endpoints testés sans erreur
- [ ] Relations JPA fonctionnelles
- [ ] Requêtes personnalisées testées
- [ ] Code commenté et documenté

---

## 🎓 Concepts Apprentis

Mapping JPA Entité ↔ Table SQL  
Relations OneToMany, ManyToMany, ManyToOne  
Spring Data JPA & Repository Pattern  
Injection de dépendances (@Autowired)  
Hibernate & Persistence Context  
JPQL/HQL automatiquement généré  
CommandLineRunner pour tests  
Properties Hibernate (ddl-auto, show-sql, etc.)  

---

## 📞 Support & Ressources

- [Spring Data JPA Docs](https://spring.io/projects/spring-data-jpa)
- [Hibernate Docs](https://hibernate.org/)
- [JPA Specification](https://jakarta.ee/specifications/persistence/)
- [Baeldung JPA Tutorials](https://www.baeldung.com/jpa-tutorial)

---

## 👨‍💻 Auteur

**Moneli AGAMAKA**  
Promo: IRA2026  


---

## 📋 Historique des Modifications

| Version | Date | Auteur | Changements |
|---------|------|--------|-------------|
| 1.0 | Avril 2024 | Moneli AGAMAKA | Création initiale - TP03|

---


