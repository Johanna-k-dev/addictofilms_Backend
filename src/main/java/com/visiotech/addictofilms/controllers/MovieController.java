package com.visiotech.addictofilms.controllers;

import com.visiotech.addictofilms.daos.MovieDao;
import com.visiotech.addictofilms.models.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies") // Endpoint de base pour accéder aux films
public class MovieController {

    private final MovieDao movieDao;

    // Injection du DAO Movie via le constructeur
    public MovieController(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    // 🔹 Récupérer tous les films
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieDao.findAll();
    }

    // 🔹 Récupérer un film par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable int id) {
        try {
            Movie movie = movieDao.findById(id);
            return ResponseEntity.ok(movie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // En cas d'erreur, retourne 404
        }
    }

    // 🔹 Ajouter un nouveau film
    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie savedMovie = movieDao.save(movie);
        return ResponseEntity.ok(savedMovie); // Retourne le film ajouté avec son ID
    }

    // 🔹 Mettre à jour un film existant
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        try {
            Movie updatedMovie = movieDao.update(id, movie);
            return ResponseEntity.ok(updatedMovie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si le film n'existe pas, retourne 404
        }
    }

    // 🔹 Supprimer un film
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable int id) {
        boolean deleted = movieDao.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Film supprimé avec succès");
        } else {
            return ResponseEntity.status(400).body("Échec de la suppression du film");
        }
    }
}
