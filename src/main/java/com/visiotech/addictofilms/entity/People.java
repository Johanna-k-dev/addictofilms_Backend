package com.visiotech.addictofilms.entity;

public class People {

    private int id; // Identifiant unique de la personne (maintenant de type int)

    private boolean adult; // Indique si la personne travaille sur des contenus adultes (true ou false)

    private String name; // Nom de la personne

    private String backdropPath; // Image d'arrière-plan

    private int gender; // Genre (0 = inconnu, 1 = homme, 2 = femme)

    private String originalName; // Nom original de la personne

    private String characterName; // Nom du personnage joué (si applicable)

    private double popularity; // Popularité de la personne (maintenant de type double)

    private String posterPath; // URL de l'image de profil

    private String knownForDepartment; // Département de spécialisation (ex: "Acting", "Directing")

    private double voteAverage; // Moyenne des votes (maintenant de type double)

    private int voteCount; // Nombre total de votes

    // 🔹 Constructeurs
    public People() {}

    public People(int id, boolean adult, String name, String backdropPath, int gender,
                  String originalName, String characterName, double popularity,
                  String posterPath, String knownForDepartment, double voteAverage, int voteCount) {
        this.id = id;
        this.adult = adult;
        this.name = name;
        this.backdropPath = backdropPath;
        this.gender = gender;
        this.originalName = originalName;
        this.characterName = characterName;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.knownForDepartment = knownForDepartment;
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

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}