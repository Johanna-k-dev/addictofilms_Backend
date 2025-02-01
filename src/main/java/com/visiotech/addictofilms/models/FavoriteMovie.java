package com.visiotech.addictofilms.models;

public class FavoriteMovie {

    private int movieId;  // ID du film favori
    private int userId;   // ID de l'utilisateur

    // 🔹 Constructeur par défaut
    public FavoriteMovie() {}

    // 🔹 Constructeur avec paramètres
    public FavoriteMovie(int movieId, int userId){
        this.movieId = movieId;
        this.userId = userId;
    }

    // 🔹 Getters et Setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
