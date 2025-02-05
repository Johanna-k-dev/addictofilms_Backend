-- Table des utilisateurs
CREATE TABLE `user` (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE, -- Adresse email unique
    password VARCHAR(255) NOT NULL -- Mot de passe
    role VARCHAR(50) NOT NULL;
);

-- Table des catégories de films
CREATE TABLE category (
    id_category INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL, -- Nom de la catégorie
    user_id INT NOT NULL, -- Référence à l'utilisateur
    CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES `user` (id_user) ON DELETE CASCADE
);

-- Table des films
CREATE TABLE movie (
    id_movie VARCHAR(255) PRIMARY KEY, -- Identifiant unique du film
    adult VARCHAR(5) NOT NULL, -- Indique si le film est pour adultes (true/false)
    title VARCHAR(255) NOT NULL, -- Titre du film
    backdrop_path VARCHAR(255), -- Image de fond (nullable)
    genre_ids TEXT NOT NULL, -- Liste des genres associés (ex: JSON)
    original_language VARCHAR(10) NOT NULL, -- Langue originale
    original_title VARCHAR(255) NOT NULL, -- Titre original
    overview TEXT NOT NULL, -- Description du film
    popularity DECIMAL(10,2) NOT NULL, -- Popularité
    poster_path VARCHAR(255), -- Affiche du film (nullable)
    video VARCHAR(5) NOT NULL, -- Indicateur de vidéo (true/false)
    vote_average DECIMAL(3,1) NOT NULL, -- Moyenne des votes
    vote_count INT NOT NULL -- Nombre de votes
);

-- Table des favoris (films préférés des utilisateurs)
CREATE TABLE favorite_movie (
    movie_id VARCHAR(255) NOT NULL, -- Identifiant du film
    user_id INT NOT NULL,
    PRIMARY KEY (movie_id, user_id), -- Clé primaire composite
    CONSTRAINT fk_favorite_movie_user FOREIGN KEY (user_id) REFERENCES `user` (id_user) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_movie_movie FOREIGN KEY (movie_id) REFERENCES movie (id_movie) ON DELETE CASCADE
);

-- Table des relations entre films et catégories
CREATE TABLE movie_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id VARCHAR(255) NOT NULL,
    category_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_movie_category_movie FOREIGN KEY (movie_id) REFERENCES movie (id_movie) ON DELETE CASCADE,
    CONSTRAINT fk_movie_category_category FOREIGN KEY (category_id) REFERENCES category (id_category) ON DELETE CASCADE,
    CONSTRAINT fk_movie_category_user FOREIGN KEY (user_id) REFERENCES `user` (id_user) ON DELETE CASCADE
);

-- Table des vues des films par utilisateur
CREATE TABLE views_movie (
    movie_id VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (movie_id, user_id), -- Clé primaire composite
    CONSTRAINT fk_views_movie FOREIGN KEY (movie_id) REFERENCES movie (id_movie) ON DELETE CASCADE,
    CONSTRAINT fk_views_user FOREIGN KEY (user_id) REFERENCES `user` (id_user) ON DELETE CASCADE
);

-- Table des personnes (acteurs, réalisateurs, etc.)
CREATE TABLE people (
    id_people VARCHAR(255) PRIMARY KEY, -- Identifiant unique
    adult VARCHAR(5) NOT NULL, -- Indique si la personne est adulte (true/false)
    name VARCHAR(255) NOT NULL, -- Nom de la personne
    backdrop_path VARCHAR(255), -- Image de fond (nullable)
    gender TINYINT(1) NOT NULL, -- Genre (0: inconnu, 1: homme, 2: femme)
    original_name VARCHAR(255) NOT NULL, -- Nom original
    character_name VARCHAR(255) NOT NULL, -- Nom du personnage joué
    popularity DECIMAL(10,2) NOT NULL, -- Popularité
    poster_path VARCHAR(255), -- Photo de profil (nullable)
    known_for_department VARCHAR(255) NOT NULL, -- Département connu (Acting, Directing, etc.)
    vote_average DECIMAL(3,1) NOT NULL, -- Moyenne des votes
    vote_count INT NOT NULL -- Nombre de votes
);

-- Table des personnes favorites
CREATE TABLE favorite_people (
    people_id VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (people_id, user_id), -- Clé primaire composite
    CONSTRAINT fk_favorite_people FOREIGN KEY (people_id) REFERENCES people (id_people) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_people_user FOREIGN KEY (user_id) REFERENCES `user` (id_user) ON DELETE CASCADE
);

-- Indexation pour améliorer la recherche
CREATE INDEX idx_movie_id ON movie_category (movie_id);
CREATE INDEX idx_category_id ON movie_category (category_id);
CREATE INDEX idx_email ON `user` (email);
CREATE INDEX idx_user_id ON favorite_movie (user_id);
CREATE INDEX idx_fav_people_user ON favorite_people (user_id);
CREATE INDEX idx_fav_movie_user ON favorite_movie (user_id);

-- Ajout de commentaires
COMMENT ON TABLE movie_category IS 'Table reliant les films aux catégories';
COMMENT ON COLUMN movie_category.movie_id IS 'ID du film, clé étrangère vers la table movie';
COMMENT ON COLUMN movie_category.category_id IS 'ID de la catégorie, clé étrangère vers la table category';
COMMENT ON TABLE views_movie IS 'Table des films visualisés par les utilisateurs';
COMMENT ON COLUMN views_movie.movie_id IS 'ID du film, clé étrangère vers la table movie';



