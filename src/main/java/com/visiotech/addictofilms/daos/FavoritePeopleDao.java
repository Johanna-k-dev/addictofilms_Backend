package com.visiotech.addictofilms.daos;

import com.visiotech.addictofilms.models.FavoritePeople;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoritePeopleDao {

    private final JdbcTemplate jdbcTemplate;

    // 🔹 Constructeur avec JdbcTemplate
    public FavoritePeopleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 🔹 Mapper pour transformer une ligne SQL en objet Java
    private final RowMapper<FavoritePeople> favoritePeopleRowMapper = (rs, rowNum) ->
            new FavoritePeople(rs.getInt("people_id"), rs.getInt("user_id"));

    // 🔹 Ajouter une personne en favori
    public void addFavorite(int userId, int peopleId) {
        String sql = "INSERT INTO favorite_people (user_id, people_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, peopleId);
    }

    // 🔹 Récupérer les personnalités favorites d'un utilisateur
    public List<FavoritePeople> getFavoritesByUserId(int userId) {
        String sql = "SELECT * FROM favorite_people WHERE user_id = ?";
        return jdbcTemplate.query(sql, favoritePeopleRowMapper, userId);
    }

    // 🔹 Supprimer une personne des favoris
    public void removeFavorite(int userId, int peopleId) {
        String sql = "DELETE FROM favorite_people WHERE user_id = ? AND people_id = ?";
        jdbcTemplate.update(sql, userId, peopleId);
    }

    public boolean existsByUserIdAndPeopleId(int userId, int movieId) {
        String sql = "SELECT COUNT(*) FROM favorite_people WHERE user_id = ? AND people_id = ?";

        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, movieId);
        return count > 0;
    }
}
