package com.visiotech.addictofilms.controllers;

import com.visiotech.addictofilms.daos.FavoritePeopleDao;
import com.visiotech.addictofilms.models.FavoritePeople;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites/people") // 📌 URL de base pour ce contrôleur
public class FavoritePeopleController {

    private final FavoritePeopleDao favoritePeopleDao;

    //  Injection du DAO dans le contrôleur
    public FavoritePeopleController(FavoritePeopleDao favoritePeopleDao) {
        this.favoritePeopleDao = favoritePeopleDao;
    }

    //  Ajouter une personne en favori
    public ResponseEntity<String> addFavorite(@RequestParam int userId, @RequestParam int peopleId) {
        // Vérifier si la personne est déjà dans les favoris de l'utilisateur
        boolean alreadyExists = favoritePeopleDao.existsByUserIdAndPeopleId(userId, peopleId);

        if (alreadyExists) {
            return ResponseEntity.badRequest().body("❌ Cette personne est déjà dans vos favoris !");
        }

        // Ajouter la personne en favori
        favoritePeopleDao.addFavorite(userId, peopleId);
        return ResponseEntity.ok("✅ Personne ajoutée aux favoris !");
    }

    //  Récupérer les personnalités favorites d'un utilisateur
    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoritePeople>> getFavorites(@PathVariable int userId) {
        List<FavoritePeople> favorites = favoritePeopleDao.getFavoritesByUserId(userId);
        return ResponseEntity.ok(favorites);
    }

    //  Supprimer une personne des favoris
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFavorite(@RequestParam int userId, @RequestParam int peopleId) {
        favoritePeopleDao.removeFavorite(userId, peopleId);
        return ResponseEntity.ok("Personne supprimée des favoris !");
    }
}
