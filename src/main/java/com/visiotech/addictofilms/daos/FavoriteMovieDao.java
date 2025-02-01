package com.visiotech.addictofilms.daos;

import com.visiotech.addictofilms.models.FavoriteMovie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FavoriteMovieDao {

    private final JdbcTemplate jdbcTemplate;


    public FavoriteMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final RowMapper<FavoriteMovie> favoriteMovieRowMapper = (rs, rowNum) ->
            new FavoriteMovie(rs.getInt("movie_id"), rs.getInt("user_id"));


    public void addFavorite(int userId, int movieId) {
        String sql = "INSERT INTO favorite_movie (user_id, movie_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, movieId);
    }


    public List<FavoriteMovie> getFavoritesByUserId(int userId) {
        String sql = "SELECT * FROM favorite_movie WHERE user_id = ?";
        return jdbcTemplate.query(sql, favoriteMovieRowMapper, userId);
    }


    public void removeFavorite(int userId, int movieId) {
        String sql = "DELETE FROM favorite_movie WHERE user_id = ? AND movie_id = ?";
        jdbcTemplate.update(sql, userId, movieId);
    }

    public boolean existsByUserIdAndMovieId(int userId, int movieId) {
        String sql = "SELECT COUNT(*) FROM favorite_movies WHERE user_id = ? AND movie_id = ?";

        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, movieId);
        return count > 0;
    }
}
