package com.visiotech.addictofilms.daos;

import com.visiotech.addictofilms.models.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Annotation indiquant que cette classe est un DAO Spring
public class MovieDao {
    private final JdbcTemplate jdbcTemplate;

    // Injection de JdbcTemplate via le constructeur (bonne pratique)
    public MovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Définition du RowMapper pour convertir une ligne SQL en objet Movie
    private final RowMapper<Movie> movieRowMapper = (rs, rowNum) -> new Movie(
            rs.getInt("id"),                        // Récupération de l'ID
            rs.getBoolean("adult"),                 // Récupération du champ 'adult'
            rs.getString("title"),                  // Récupération du titre
            rs.getString("backdrop_path"),          // Récupération du fond d'écran
            rs.getString("genre_ids"),              // Récupération des genres sous forme de String
            rs.getString("original_language"),      // Récupération de la langue originale
            rs.getString("original_title"),         // Récupération du titre original
            rs.getString("overview"),               // Récupération du résumé
            rs.getDouble("popularity"),             // Récupération de la popularité
            rs.getString("poster_path"),            // Récupération de l'affiche
            rs.getBoolean("video"),                  // Récupération du champ vidéo
            rs.getString("vote_average"),           // Récupération de la moyenne des votes
            rs.getInt("vote_count")                 // Récupération du nombre de votes
    );


    public List<Movie> findAll() {
        String sql = "SELECT * FROM movie";
        return jdbcTemplate.query(sql, movieRowMapper);
    }


    public Movie findById(int id) {
        String sql = "SELECT * FROM movie WHERE id = ?";
        return jdbcTemplate.query(sql, movieRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Le film avec l'ID : " + id + " n'existe pas"));
    }


    public Movie save(Movie movie) {
        String sql = "INSERT INTO movie (title, adult, backdrop_path, genre_ids, original_language, " +
                "original_title, overview, popularity, poster_path, video, vote_average, vote_count) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getAdult(), movie.getBackdropPath(),
                movie.getGenreIds(), movie.getOriginalLanguage(), movie.getOriginalTitle(),
                movie.getOverview(), movie.getPopularity(), movie.getPosterPath(),
                movie.getVideo(), movie.getVoteAverage(), movie.getVoteCount());


        String sqlGetId = "SELECT LAST_INSERT_ID()";
        int id = jdbcTemplate.queryForObject(sqlGetId, Integer.class);

        movie.setId(id);
        return movie;
    }


    public Movie update(int id, Movie movie) {
        if (!movieExists(id)) {
            throw new RuntimeException("Le film avec l'ID : " + id + " n'existe pas");
        }

        String sql = "UPDATE movie SET title = ?, adult = ?, backdrop_path = ?, genre_ids = ?, " +
                "original_language = ?, original_title = ?, overview = ?, popularity = ?, " +
                "poster_path = ?, video = ?, vote_average = ?, vote_count = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, movie.getTitle(), movie.getAdult(), movie.getBackdropPath(),
                movie.getGenreIds(), movie.getOriginalLanguage(), movie.getOriginalTitle(),
                movie.getOverview(), movie.getPopularity(), movie.getPosterPath(),
                movie.getVideo(), movie.getVoteAverage(), movie.getVoteCount(), id);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour du film avec l'ID : " + id);
        }

        return this.findById(id);
    }


    public boolean delete(int id) {
        String sql = "DELETE FROM movie WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }


    private boolean movieExists(int id) {
        String checkSql = "SELECT COUNT(*) FROM movie WHERE id = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);
        return count > 0;
    }
}
