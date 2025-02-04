package com.visiotech.addictofilms.models;

public class Category {

    private int id;
    private String name;
    private int userId;

    public Category() {}
    public Category(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId(){
        return  id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
