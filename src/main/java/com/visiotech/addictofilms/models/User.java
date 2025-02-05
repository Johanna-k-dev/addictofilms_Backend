package com.visiotech.addictofilms.models;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class User {

    private Long id;

    @NotBlank(message= "Vous devez renseigner une adresse email")
    @Email(message = "le Format de l'email n'est pas valide")
    private String email;
    @NotBlank(message= "Vous devez renseigner un mot de passe")
    @Size(min = 8 , message = "Vous devez entrer 8 caractères minimum")
    private String password;

    public User(){}

    public User (Long id, String email, String password){
        this.id = id;
        this.email= email;
        this.password = password;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
