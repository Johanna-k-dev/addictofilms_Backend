package com.visiotech.addictofilms.models;

import java.time.LocalDateTime;

public class ViewsMovie {

    private int id;  // Identifiant unique de la vue
    private int movieId;  // Référence au film
    private int userId;  // Référence à l'utilisateur
    private LocalDateTime viewedAt;  // Date et heure de la vue

    // Constructeur par défaut
    public ViewsMovie() {}

    // Constructeur avec paramètres
    public ViewsMovie(int id, int movieId, int userId, LocalDateTime viewedAt) {
        this.id = id;
        this.movieId = movieId;
        this.userId = userId;
        this.viewedAt = viewedAt;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    // Méthode toString pour affichage
    @Override
    public String toString() {
        return "ViewsMovie{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", userId=" + userId +
                ", viewedAt=" + viewedAt +
                '}';
    }
}
