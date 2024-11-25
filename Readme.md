
# Projet Citronix

## Contexte du Projet

Citronix est une application de gestion pour une ferme de citrons, visant à faciliter le suivi de la production, de la récolte, et de la vente des produits. Elle permet aux fermiers d'optimiser la gestion des fermes, des champs, des arbres, des récoltes, et des ventes, tout en évaluant la productivité des arbres en fonction de leur âge.

---

## Fonctionnalités Principales

### Gestion des Fermes
- **CRUD** des informations d'une ferme (nom, localisation, superficie, date de création).
- Recherche multicritère utilisant `Criteria Builder`.

### Gestion des Champs
- Association des champs à une ferme avec une superficie définie.
- Validation de la cohérence des superficies :
    - La somme des superficies des champs doit être strictement inférieure à celle de la ferme.

### Gestion des Arbres
- Suivi des arbres avec leur date de plantation, leur âge, et leur champ d'appartenance.
- Calcul automatique de l'âge des arbres.
- Évaluation de la productivité annuelle selon l'âge :
    - Jeune (< 3 ans) : **2,5 kg / saison**.
    - Mature (3-10 ans) : **12 kg / saison**.
    - Vieux (> 10 ans) : **20 kg / saison**.

### Gestion des Récoltes
- Suivi des récoltes par saison (hiver, printemps, été, automne).
- Limitation : Une seule récolte par saison.
- Enregistrement de la date de récolte et de la quantité totale récoltée.

### Détail des Récoltes
- Suivi des quantités récoltées par arbre pour une récolte donnée.
- Association des détails de récolte à un arbre spécifique.

### Gestion des Ventes
- Enregistrement des ventes (date, prix unitaire, client, et récolte associée).
- Calcul automatique du revenu : **Revenu = quantité * prixUnitaire**.

---

## Contraintes

### Superficie
- **Minimale** : 0,1 hectare (1 000 m²) par champ.
- **Maximale** : 50% de la superficie totale de la ferme.

### Gestion des Champs et Arbres
- Une ferme ne peut contenir plus de **10 champs**.
- Densité des arbres : **100 arbres / hectare**.
- Durée de vie maximale : Un arbre est considéré non productif après **20 ans**.
- Période de plantation : Uniquement entre **mars** et **mai**.

### Récoltes
- Une récolte par champ et par saison.
- Les arbres ne peuvent être inclus dans plus d'une récolte par saison.

---

## Exigences Techniques

- **Framework** : Spring Boot.
- **Architecture** : En couches (Controller, Service, Repository, Entity).
- **Validation** : Via annotations Spring.
- **Conversion** : Utilisation de MapStruct pour convertir les entités en DTO/View Models.
- **Gestion des Exceptions** : Centralisée.
- **Tests** : Tests unitaires avec JUnit et Mockito.
- **Entités** : Simplification via Lombok et le Builder Pattern.

---

## Installation et Exécution

1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/ATalemsi/Citronix_SpringBoot.git
   cd Citronix_SpringBoot
   ```

2. **Configurer la base de données** :
    - Modifier le fichier `application.yaml` avec les détails de votre base de données.

3. **Lancer l'application** :
   ```bash
   mvn spring-boot:run
   ```

4. **Accéder à l'API** :
    - URL de base : `http://localhost:8080`

---

## Tests

- Exécuter les tests unitaires :
  ```bash
  mvn test
  ```

- Générer un rapport de couverture Jacoco :
  ```bash
  mvn verify
  ```
  Ouvrir le fichier `target/site/jacoco/index.html` dans un navigateur pour visualiser le rapport.

---

## Auteur
Projet développé dans le cadre du projet Citronix. Contactez ATalemsi(username sur Github) pour plus d'informations.
