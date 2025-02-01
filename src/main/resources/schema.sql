-- Création de la base de données si elle n'existe pas
CREATE DATABASE IF NOT EXISTS addictofilms;

-- Sélection de la base de données
USE addictofilms;

-- Table des utilisateurs
CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE, -- Adresse email unique
    password VARCHAR(255) NOT NULL, -- Mot de passe
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Date de création
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Dernière mise à jour
    failed_login_attempts INT DEFAULT 3, -- Tentatives de connexion échouées
    last_login_attempt TIMESTAMP NULL -- Dernière tentative de connexion
);

-- Table des catégories de films
CREATE TABLE category (
    id_category INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL, -- Nom de la catégorie
    user_id INT NOT NULL, -- Référence à l'utilisateur
    CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users (id_user) ON DELETE CASCADE
);

-- Table des films
CREATE TABLE movie (
    id VARCHAR(255) PRIMARY KEY, -- Identifiant unique du film
    adult VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL, -- Titre du film
    backdrop_path VARCHAR(255) NOT NULL, -- Image de fond
    genre_ids VARCHAR(255) NOT NULL, -- Liste des genres associés
    original_language VARCHAR(255) NOT NULL, -- Langue originale
    original_title VARCHAR(255) NOT NULL, -- Titre original
    overview VARCHAR(255) NOT NULL, -- Description du film
    popularity DECIMAL(10,2) NOT NULL, -- Popularité
    poster_path VARCHAR(255) NOT NULL, -- Affiche du film
    video VARCHAR(255) NOT NULL, -- Indicateur de vidéo
    vote_average DECIMAL(3,1) NOT NULL, -- Moyenne des votes
    vote_count INT NOT NULL -- Nombre de votes
);

-- Table des favoris (films préférés des utilisateurs)
CREATE TABLE favorite_movie (
    id VARCHAR(255) PRIMARY KEY, -- Identifiant unique
    user_id INT NOT NULL,
    CONSTRAINT fk_favorite_movie_user FOREIGN KEY (user_id) REFERENCES users (id_user) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_movie_movie FOREIGN KEY (id) REFERENCES movie (id) ON DELETE CASCADE
);

-- Table des relations entre films et catégories
CREATE TABLE movie_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id VARCHAR(255) NOT NULL,
    category_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_movie_category_movie FOREIGN KEY (movie_id) REFERENCES movie (id),
    CONSTRAINT fk_movie_category_category FOREIGN KEY (category_id) REFERENCES category (id_category),
    CONSTRAINT fk_movie_category_user FOREIGN KEY (user_id) REFERENCES users (id_user) ON DELETE CASCADE
);

-- Table des vues des films par utilisateur
CREATE TABLE views (
    id VARCHAR(255) PRIMARY KEY,
    user_id INT NOT NULL,
    CONSTRAINT fk_views_movie FOREIGN KEY (id) REFERENCES movie (id),
    CONSTRAINT fk_views_user FOREIGN KEY (user_id) REFERENCES users (id_user) ON DELETE CASCADE
);

-- Table des personnes (acteurs, réalisateurs, etc.)
CREATE TABLE people (
    id VARCHAR(255) PRIMARY KEY,
    adult VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    backdrop_path VARCHAR(255) NOT NULL,
    gender TINYINT(1) NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    character_name VARCHAR(255) NOT NULL,
    popularity DECIMAL(10,2) NOT NULL,
    poster_path VARCHAR(255) NOT NULL,
    known_for_department VARCHAR(255) NOT NULL,
    vote_average DECIMAL(3,1) NOT NULL,
    vote_count INT NOT NULL
);

-- Table des personnes favorites
CREATE TABLE favorite_people (
    id VARCHAR(255) PRIMARY KEY,
    user_id INT NOT NULL,
    CONSTRAINT fk_favorite_people FOREIGN KEY (id) REFERENCES people (id),
    CONSTRAINT fk_favorite_people_user FOREIGN KEY (user_id) REFERENCES users (id_user) ON DELETE CASCADE
);

-- Indexation pour améliorer la recherche
CREATE INDEX idx_movie_id ON movie_category (movie_id);
CREATE INDEX idx_email ON users (email);
CREATE INDEX idx_user_id ON favorite_movie (user_id);

COMMENT ON TABLE movie_category IS 'Table reliant les films aux catégories';
COMMENT ON COLUMN movie_category.movie_id IS 'ID du film, clé étrangère vers la table movie';
COMMENT ON COLUMN movie_category.category_id IS 'ID de la catégorie, clé étrangère vers la table category';
