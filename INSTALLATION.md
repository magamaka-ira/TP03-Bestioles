# TP03 - GUIDE D'INSTALLATION COMPLET

## 📋 Configuration Requise

- **Java**: JDK 17 ou supérieur
- **Maven**: 3.6+ (ou utiliser `mvnw`)
- **MySQL/MariaDB**: 5.7+ (ou version récente)
- **IDE**: VS Code, STS, IntelliJ IDEA
- **Git**: (optionnel, pour versionner)

Vérifier les versions:
```bash
java -version
mvnw --version
mysql --version
```

---

## 🛠️ Étape 1: Créer la Base de Données

### Méthode 1: Via MySQL Command Line
```bash
# Ouvrir mysql
mysql -u root -p

# Créer la base
CREATE DATABASE bestioles DEFAULT CHARACTER SET utf8mb4;
USE bestioles;

# Importer le dump SQL
SOURCE C:/path/to/database/dump-bestioles.sql;

# Vérifier
SHOW TABLES;
SELECT * FROM species;
```

### Méthode 2: Via PhpMyAdmin
1. Accéder à http://localhost/phpmyadmin
2. Créer une nouvelle base "bestioles"
3. Importer le fichier SQL via l'interface
4. Vérifier les tables

### Méthode 3: Via HeidiSQL (Graphique)
1. Ouvrir HeidiSQL
2. Session → Nouvelle → MySQL
3. Créer base "bestioles"
4. Fichier → Exécuter fichier SQL → database/dump-bestioles.sql

---

## 📂 Étape 2: Configurer le Projet

### Structure attendue:
```
TP03/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── model/         (4 entités)
│   │   │   ├── repository/    (4 repos)
│   │   │   └── SpringDemoApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── database/
│   └── dump-bestioles.sql
├── pom.xml
├── README.md
└── .gitignore
```

### Vérifier application.properties:
```properties
# Base de données
spring.datasource.url=jdbc:mysql://localhost:3306/bestioles
spring.datasource.username=root
spring.datasource.password=root  # Modifier si différent

# JPA
spring.jpa.hibernate.ddl-auto=validate  # IMPORTANT: ne pas modifier la BDD
```

---

## 🔧 Étape 3: Compiler le Projet

```bash
# Aller dans le dossier
cd TP03

# Compiler
mvnw clean compile

# Ou avec Gradle
gradle clean build --no-test
```

**Résultat attendu:** Aucune erreur

---

## ▶️ Étape 4: Lancer l'Application

```bash
# Via Maven
mvnw spring-boot:run

# Via gradle
gradle bootRun

# Via Java (après compilation)
java -jar target/spring-tp03-bestioles-1.0.0.jar
```

**Résultat attendu:**
```
================================================================================
TP03 - SPRING DATA JPA & REPOSITORIES - TESTS
Étudiant: Moneli AGAMAKA IRA2026
Date: Décembre 2024
================================================================================

📌 TEST 1: SpeciesRepository
...
✅ TESTS TERMINÉS AVEC SUCCÈS
```

---

## 🧪 Étape 5: Vérifier les Tests

L'application s'exécute et affiche:

### ✅ TEST 1: SpeciesRepository
- Récupère 3 espèces (Chat, Chien, Lapin)
- Recherche par nom
- Compte total

### ✅ TEST 2: AnimalRepository
- Récupère 6 animaux
- Filtre par espèce
- Filtre par sexe
- Compte

### ✅ TEST 3: PersonRepository
- Récupère 8 personnes
- Recherche par login
- Filtre par statut actif
- Compte

### ✅ TEST 4: RoleRepository
- Récupère 3 rôles
- Recherche par label

---

## 🐛 Dépannage

### ❌ Erreur: "Connection refused"
```
Cause: MySQL n'est pas actif
Solution:
  - Démarrer MySQL: net start MySQL80 (Windows)
  - Ou: brew services start mysql (Mac)
  - Ou: sudo service mysql start (Linux)
```

### ❌ Erreur: "Unknown database 'bestioles'"
```
Cause: Base de données non créée
Solution: Exécuter le dump SQL (voir Étape 1)
```

### ❌ Erreur: "Access denied for user 'root'"
```
Cause: Mauvais mot de passe
Solution: Vérifier application.properties
  spring.datasource.password=<VOTRE_MDP>
```

### ❌ Erreur: "No qualifying bean of type"
```
Cause: Repository non reconnu
Solution:
  - Vérifier que les repositories sont dans bon package
  - Vérifier @Repository sur l'interface
  - Redémarrer l'IDE
```

### ❌ Erreur: "Circular dependency"
```
Cause: Relations ManyToMany mal configurées
Solution:
  - Vérifier mappedBy correct
  - Utiliser @JsonIgnore si nécessaire
```

---

## 📊 Vérifier les Données

```sql
-- Dans MySQL, vérifier les données:
USE bestioles;

-- Espèces
SELECT * FROM species;

-- Animaux
SELECT a.id, a.name, a.color, a.sex, s.common_name 
FROM animal a
JOIN species s ON a.species_id = s.id;

-- Personnes
SELECT * FROM person WHERE active = 1;

-- Rôles
SELECT * FROM role;

-- Associations Person-Animal
SELECT p.firstname, p.lastname, a.name 
FROM person_animals pa
JOIN person p ON pa.person_id = p.id
JOIN animal a ON pa.animals_id = a.id;
```

---

## 🚀 Workflows Courants

### Modifier une entité
1. Modifier la classe `.java`
2. Redémarrer l'application (Ctrl+Shift+F9 ou Ctrl+C + relancer)
3. Les requêtes valideront la structure

### Ajouter une requête dans un Repository
1. Ajouter une méthode dans l'interface
2. Le nom de la méthode génère automatiquement la requête SQL
3. Pas besoin de redémarrer, compilé à la volée

### Tester une requête custom
```java
// Dans CommandLineRunner
List<Animal> cats = animalRepository.findByColor("Noir");
cats.forEach(a -> System.out.println(a.getName()));
```

---

## 💾 Sauvegarder sur GitHub

```bash
# Initialiser git (si pas fait)
git init

# Ajouter les fichiers
git add .

# Commit initial
git commit -m "TP03: Spring Data JPA - Initial commit"

# Créer un repo sur GitHub.com

# Ajouter origin
git remote add origin https://github.com/YOUR_GITHUB/TP03-Bestioles.git

# Pousser
git branch -M main
git push -u origin main
```

---

## 📚 Fichiers Importants

| Fichier | Description |
|---------|-------------|
| `pom.xml` | Dépendances Maven |
| `application.properties` | Configuration DataSource |
| `dump-bestioles.sql` | Script d'initialisation BD |
| `SpringDemoApplication.java` | Classe principale + Tests |
| `*/model/*.java` | Entités JPA (4 fichiers) |
| `*/repository/*.java` | Repositories (4 fichiers) |
| `README.md` | Documentation complète |

---

## ✅ Checklist Post-Installation

- [ ] Java 17+ installé
- [ ] Maven/mvnw disponible
- [ ] Base de données 'bestioles' créée
- [ ] Dump SQL importé (5 tables créées)
- [ ] application.properties configuré
- [ ] Compilation sans erreur: `mvnw clean compile`
- [ ] Application démarre: `mvnw spring-boot:run`
- [ ] Tests affichent les résultats
- [ ] Toutes les 4 espèces affichées
- [ ] Tous les 6 animaux affichés
- [ ] Toutes les 8 personnes affichées
- [ ] Tous les 3 rôles affichés
- [ ] Pas d'erreurs dans les logs

---

## 🎯 Prochaines Étapes

Après TP03:
1. Ajouter une couche Service (BusinessLogic)
2. Créer une REST API (@RestController)
3. Ajouter la pagination
4. Ajouter la validation (@Valid)
5. Ajouter la sécurité (Spring Security)

---

## 📞 Support

En cas de problème:
1. Vérifier les logs de la console
2. Vérifier application.properties
3. Vérifier la base de données existe
4. Vérifier les permissions MySQL
5. Vérifier le format des fichiers .java
6. Redémarrer l'IDE complètement

---

**Installation Terminée ✅** - L'application est prête à l'emploi!
