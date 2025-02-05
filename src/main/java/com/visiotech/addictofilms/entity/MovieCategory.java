package com.visiotech.addictofilms.entity;



public class MovieCategory {

    private int id;
    private int movieId;
    private String categoryName;
    private int userId;

    public MovieCategory() {}

    public MovieCategory(int id,int movieId, String category, int userId) {
        this.id=id;
        this.movieId = movieId;
        this.categoryName = category;
        this.userId = userId;
    }
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

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String category) {
        this.categoryName = category;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
