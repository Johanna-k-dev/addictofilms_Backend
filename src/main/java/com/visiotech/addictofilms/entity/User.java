package com.visiotech.addictofilms.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class User {

    private Long id;

    @NotBlank(message= "Vous devez renseigner une adresse email")
    @Email(message = "Le format de l'email n'est pas valide")
    private String email;

    @NotBlank(message= "Vous devez renseigner un mot de passe")
    @Size(min = 8 , message = "Vous devez entrer 8 caractères minimum")
    private String password;

    private String role;

    // ✅ Constructeur vide (nécessaire pour Hibernate)
    public User() {}

    // ✅ Constructeur avec ID
    public User(Long id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ✅ 🚀 Constructeur manquant (maintenant ajouté !)
    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ✅ Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
