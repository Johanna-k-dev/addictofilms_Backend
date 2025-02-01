package com.visiotech.addictofilms.models;

public class Movie {
    private int id; // L'identifiant unique du film

    private Boolean adult; // Indique si le film est pour adultes ("true" ou "false")

    private String title; // Titre du film

    private String backdropPath; // URL du fond d'écran (backdrop)

    private String genreIds; // Liste des genres sous forme de String

    private String originalLanguage; // Langue originale du film

    private String originalTitle; // Titre original du film

    private String overview; // Résumé du film

    private Double popularity; // Popularité du film (nombre décimal)

    private String posterPath; // URL de l'affiche du film

    private Boolean video; // Indique si c'est une vidéo ("true" ou "false")

    private String voteAverage; // Moyenne des votes (String, car VARCHAR dans la DB)

    private int voteCount; // Nombre total de votes

    // 🔹 Constructeurs
    public Movie() {}

    public Movie(int id, Boolean adult, String title, String backdropPath, String genreIds,
                 String originalLanguage, String originalTitle, String overview, Double popularity,
                 String posterPath, Boolean video, String voteAverage, int voteCount) {
        this.id = id;
        this.adult = adult;
        this.title = title;
        this.backdropPath = backdropPath;
        this.genreIds = genreIds;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    // 🔹 Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(String genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}
