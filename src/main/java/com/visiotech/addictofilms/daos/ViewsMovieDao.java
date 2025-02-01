package com.visiotech.addictofilms.daos;

import com.visiotech.addictofilms.models.ViewsMovie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ViewsMovieDao {
    private final JdbcTemplate jdbcTemplate;


    public ViewsMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final RowMapper<ViewsMovie> viewsMovieRowMapper = (rs, rowNum) -> new ViewsMovie(
            rs.getInt("id"),                // Récupération de l'ID
            rs.getInt("movie_id"),          // Récupération de l'ID du film
            rs.getInt("user_id"),           // Récupération de l'ID de l'utilisateur
            rs.getTimestamp("viewed_at").toLocalDateTime() // Conversion en LocalDateTime
    );


    public List<ViewsMovie> findAll() {
        String sql = "SELECT * FROM views_movie";
        return jdbcTemplate.query(sql, viewsMovieRowMapper);
    }


    public ViewsMovie findById(int id) {
        String sql = "SELECT * FROM views_movie WHERE id = ?";
        return jdbcTemplate.query(sql, viewsMovieRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("La vue avec l'ID : " + id + " n'existe pas"));
    }


    public ViewsMovie save(ViewsMovie viewsMovie) {
        String sql = "INSERT INTO views_movie (movie_id, user_id, viewed_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, viewsMovie.getMovieId(), viewsMovie.getUserId(), viewsMovie.getViewedAt());


        String sqlGetId = "SELECT LAST_INSERT_ID()";
        int id = jdbcTemplate.queryForObject(sqlGetId, Integer.class);

        viewsMovie.setId(id);
        return viewsMovie;
    }


    public ViewsMovie update(int id, ViewsMovie viewsMovie) {
        if (!viewExists(id)) {
            throw new RuntimeException("La vue avec l'ID : " + id + " n'existe pas");
        }

        String sql = "UPDATE views_movie SET movie_id = ?, user_id = ?, viewed_at = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, viewsMovie.getMovieId(), viewsMovie.getUserId(), viewsMovie.getViewedAt(), id);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour de la vue avec l'ID : " + id);
        }

        return this.findById(id);
    }


    public boolean delete(int id) {
        String sql = "DELETE FROM views_movie WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }


    private boolean viewExists(int id) {
        String sql = "SELECT COUNT(*) FROM views_movie WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}
