package com.visiotech.addictofilms.models;



public class MovieCategory {

    private int id;  // Identifiant unique de la relation


    private Movie movie;  // Référence au film


    private Category category;  // Référence à la catégorie


    private User userId;  // Référence à l'utilisateur

    // Constructeurs
    public MovieCategory() {}

    public MovieCategory(Movie movie, Category category, User userId) {
        this.movie = movie;
        this.category = category;
        this.userId = userId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return userId;
    }

    public void setUser(User user) {
        this.userId = user;
    }
}
