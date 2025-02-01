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
            rs.getInt("id"),
            rs.getBoolean("adult"),
            rs.getString("name"),
            rs.getString("backdrop_path"),
            rs.getInt("gender"),
            rs.getString("original_name"),
            rs.getString("character_name"),
            rs.getDouble("popularity"),
            rs.getString("poster_path"),
            rs.getString("known_for_department"),
            rs.getDouble("vote_average"),
            rs.getInt("vote_count")
    );

    public List<People> findAll() {
        String sql = "SELECT * FROM people";
        return jdbcTemplate.query(sql, peopleRowMapper);
    }

    public People findById(int id) {
        String sql = "SELECT * FROM people WHERE id = ?";
        return jdbcTemplate.query(sql, peopleRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("La personne avec l'ID : " + id + " n'existe pas"));
    }

    public People save(People people) {
        String sql = "INSERT INTO people (name, adult, backdrop_path, gender, original_name, " +
                "character_name, popularity, poster_path, known_for_department, vote_average, vote_count) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, people.getName(), people.isAdult(), people.getBackdropPath(),
                people.getGender(), people.getOriginalName(), people.getCharacterName(),
                people.getPopularity(), people.getPosterPath(), people.getKnownForDepartment(),
                people.getVoteAverage(), people.getVoteCount());

        String sqlGetId = "SELECT LAST_INSERT_ID()";
        int id = jdbcTemplate.queryForObject(sqlGetId, Integer.class);

        people.setId(id);
        return people;
    }


    public People update(int id, People people) {
        if (!peopleExists(id)) {
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

        return this.findById(id);
    }


    public boolean delete(int id) {
        String sql = "DELETE FROM people WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }


    private boolean peopleExists(int id) {
        String checkSql = "SELECT COUNT(*) FROM people WHERE id = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);
        return count > 0;
    }
}
