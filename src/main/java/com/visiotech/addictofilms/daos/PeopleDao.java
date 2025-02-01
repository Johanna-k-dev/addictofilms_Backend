package com.visiotech.addictofilms.daos;

import com.visiotech.addictofilms.models.People;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Annotation indiquant que cette classe est un DAO Spring
public class PeopleDao {
    private final JdbcTemplate jdbcTemplate;

    // Injection de JdbcTemplate via le constructeur
    public PeopleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Définition du RowMapper pour convertir une ligne SQL en objet People
    private final RowMapper<People> peopleRowMapper = (rs, rowNum) -> new People(
            rs.getInt("id"),                 // Récupération de l'ID
            rs.getBoolean("adult"),          // Récupération du champ 'adult'
            rs.getString("name"),            // Récupération du nom
            rs.getString("backdrop_path"),   // Récupération de l'arrière-plan
            rs.getInt("gender"),             // Récupération du genre
            rs.getString("original_name"),   // Récupération du nom original
            rs.getString("character_name"),  // Récupération du nom du personnage
            rs.getDouble("popularity"),      // Récupération de la popularité
            rs.getString("poster_path"),     // Récupération du chemin de l'image du profil
            rs.getString("known_for_department"), // Récupération du département
            rs.getDouble("vote_average"),    // Récupération de la moyenne des votes
            rs.getInt("vote_count")          // Récupération du nombre de votes
    );

    // Méthode pour récupérer toutes les personnes
    public List<People> findAll() {
        String sql = "SELECT * FROM people";
        return jdbcTemplate.query(sql, peopleRowMapper);
    }

    // Méthode pour récupérer une personne par son ID
    public People findById(int id) {
        String sql = "SELECT * FROM people WHERE id = ?";
        return jdbcTemplate.query(sql, peopleRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("La personne avec l'ID : " + id + " n'existe pas"));
    }

    // Méthode pour enregistrer une nouvelle personne
    public People save(People people) {
        String sql = "INSERT INTO people (name, adult, backdrop_path, gender, original_name, " +
                "character_name, popularity, poster_path, known_for_department, vote_average, vote_count) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, people.getName(), people.isAdult(), people.getBackdropPath(),
                people.getGender(), people.getOriginalName(), people.getCharacterName(),
                people.getPopularity(), people.getPosterPath(), people.getKnownForDepartment(),
                people.getVoteAverage(), people.getVoteCount());

        // Récupérer l'ID auto-généré
        String sqlGetId = "SELECT LAST_INSERT_ID()";
        int id = jdbcTemplate.queryForObject(sqlGetId, Integer.class);

        people.setId(id); // Mise à jour de l'objet avec l'ID généré
        return people;
    }

    // Méthode pour mettre à jour une personne existante
    public People update(int id, People people) {
        if (!peopleExists(id)) { // Vérification de l'existence de la personne
            throw new RuntimeException("La personne avec l'ID : " + id + " n'existe pas");
        }

        String sql = "UPDATE people SET name = ?, adult = ?, backdrop_path = ?, gender = ?, " +
                "original_name = ?, character_name = ?, popularity = ?, poster_path = ?, " +
                "known_for_department = ?, vote_average = ?, vote_count = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, people.getName(), people.isAdult(), people.getBackdropPath(),
                people.getGender(), people.getOriginalName(), people.getCharacterName(),
                people.getPopularity(), people.getPosterPath(), people.getKnownForDepartment(),
                people.getVoteAverage(), people.getVoteCount(), id);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour de la personne avec l'ID : " + id);
        }

        return this.findById(id); // Retourne la personne mise à jour
    }

    // Méthode pour supprimer une personne
    public boolean delete(int id) {
        String sql = "DELETE FROM people WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0; // Retourne vrai si la suppression a réussi
    }

    // Méthode privée pour vérifier si une personne existe en base
    private boolean peopleExists(int id) {
        String checkSql = "SELECT COUNT(*) FROM people WHERE id = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);
        return count > 0;
    }
}
