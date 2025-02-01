package com.visiotech.addictofilms.daos;

import com.visiotech.addictofilms.models.MovieCategory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieCategoryDao {

    private final JdbcTemplate jdbcTemplate;


    public MovieCategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final RowMapper<MovieCategory> movieCategoryRowMapper = (rs, rowNum) -> new MovieCategory(
            rs.getInt("id"),                      // Récupération de l'ID de la relation
            rs.getInt("movie_id"),                 // Récupération de l'ID du film
            rs.getString("category_name"),         // Récupération du nom de la catégorie
            rs.getInt("user_id")                   // Récupération de l'ID de l'utilisateur
    );

    // Méthode pour récupérer toutes les relations MovieCategory
    public List<MovieCategory> findAll() {
        String sql = "SELECT * FROM movie_category";
        return jdbcTemplate.query(sql, movieCategoryRowMapper);
    }


    public MovieCategory findById(int id) {
        String sql = "SELECT * FROM movie_category WHERE id = ?";
        return jdbcTemplate.query(sql, movieCategoryRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("La relation MovieCategory avec l'ID : " + id + " n'existe pas"));
    }

    public MovieCategory save(MovieCategory movieCategory) {
        String sql = "INSERT INTO movie_category (movie_id, category_name, user_id) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, movieCategory.getMovieId(), movieCategory.getCategoryName(), movieCategory.getUserId());


        String sqlGetId = "SELECT LAST_INSERT_ID()";
        int id = jdbcTemplate.queryForObject(sqlGetId, Integer.class);

        movieCategory.setId(id);
        return movieCategory;
    }

    public MovieCategory update(int id, MovieCategory movieCategory) {
        if (!movieCategoryExists(id)) {
            throw new RuntimeException("La relation MovieCategory avec l'ID : " + id + " n'existe pas");
        }

        String sql = "UPDATE movie_category SET movie_id = ?, category_name = ?, user_id = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, movieCategory.getMovieId(), movieCategory.getCategoryName(),
                movieCategory.getUserId(), id);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour de la relation MovieCategory avec l'ID : " + id);
        }

        return this.findById(id);
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM movie_category WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    private boolean movieCategoryExists(int id) {
        String checkSql = "SELECT COUNT(*) FROM movie_category WHERE id = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);
        return count > 0;
    }
}
