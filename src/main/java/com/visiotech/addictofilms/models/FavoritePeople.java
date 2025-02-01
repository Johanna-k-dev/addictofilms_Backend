package com.visiotech.addictofilms.models;

public class FavoritePeople {

    private int peopleId;
    private int userId;

    public FavoritePeople(){}



    public FavoritePeople(int id, int userId){
        this.peopleId = id;
        this.userId = userId;
    }

    public int getPeopleId() {
        return peopleId;
    }

    public void setId(int id) {
        this.peopleId = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
