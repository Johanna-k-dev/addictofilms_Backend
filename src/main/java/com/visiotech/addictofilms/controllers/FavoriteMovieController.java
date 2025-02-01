package com.visiotech.addictofilms.controllers;

import com.visiotech.addictofilms.daos.FavoriteMovieDao;
import com.visiotech.addictofilms.models.FavoriteMovie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites/movies") // 📌 URL de base pour ce contrôleur
public class FavoriteMovieController {

    private final FavoriteMovieDao favoriteMovieDao;

    // 🔹 Injection du DAO dans le contrôleur
    public FavoriteMovieController(FavoriteMovieDao favoriteMovieDao) {
        this.favoriteMovieDao = favoriteMovieDao;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFavorite(@RequestParam int userId, @RequestParam int movieId) {
        // Vérifier si le film est déjà dans les favoris de l'utilisateur
        boolean alreadyExists = favoriteMovieDao.existsByUserIdAndMovieId(userId, movieId);

        if (alreadyExists) {
            return ResponseEntity.badRequest().body("❌ Ce film est déjà dans vos favoris !");
        }

        // Ajouter le film en favori
        favoriteMovieDao.addFavorite(userId, movieId);
        return ResponseEntity.ok("✅ Film ajouté aux favoris !");
    }


    // 🔹 Récupérer les films favoris d'un utilisateur
    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteMovie>> getFavorites(@PathVariable int userId) {
        List<FavoriteMovie> favorites = favoriteMovieDao.getFavoritesByUserId(userId);
        return ResponseEntity.ok(favorites);
    }

    // 🔹 Supprimer un film des favoris
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFavorite(@RequestParam int userId, @RequestParam int movieId) {
        favoriteMovieDao.removeFavorite(userId, movieId);
        return ResponseEntity.ok("Film supprimé des favoris !");
    }
}
