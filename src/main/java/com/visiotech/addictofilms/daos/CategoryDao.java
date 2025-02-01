package com.visiotech.addictofilms.daos;

import com.visiotech.addictofilms.models.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Annotation indiquant que cette classe est un DAO Spring
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    // Injection de JdbcTemplate via le constructeur (bonne pratique)
    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Définition du RowMapper pour convertir une ligne SQL en objet Category
    private final RowMapper<Category> categoryRowMapper = (rs, rowNum) -> new Category(
            rs.getInt("id"),         // Récupération de l'ID
            rs.getString("name"),    // Récupération du nom
            rs.getInt("user_id")     // Récupération de l'ID utilisateur (correction du nom de colonne)
    );

    // Méthode pour récupérer toutes les catégories
    public List<Category> findAll() {
        String sql = "SELECT * FROM category";
        return jdbcTemplate.query(sql, categoryRowMapper);
    }

    // Méthode pour récupérer une catégorie par son ID
    public Category findById(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        return jdbcTemplate.query(sql, categoryRowMapper, new Object[]{id})
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("La catégorie avec l'ID : " + id + " n'existe pas"));
    }

    // Méthode pour enregistrer une nouvelle catégorie
    public Category save(Category category) {
        String sql = "INSERT INTO category (name, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, category.getName(), category.getUserId());

        // Récupérer l'ID auto-généré si applicable (MySQL)
        String sqlGetId = "SELECT LAST_INSERT_ID()";
        int id = jdbcTemplate.queryForObject(sqlGetId, Integer.class);

        category.setId(id); // Mise à jour de l'objet avec l'ID généré
        return category;
    }

    // Méthode pour mettre à jour une catégorie existante
    public Category update(int id, Category category) {
        if (!categoryExists(id)) { // Vérification de l'existence de la catégorie
            throw new RuntimeException("La catégorie avec l'ID : " + id + " n'existe pas");
        }

        String sql = "UPDATE category SET name = ?, user_id = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, category.getName(), category.getUserId(), id);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour de la catégorie avec l'ID : " + id);
        }

        return this.findById(id); // Retourne la catégorie mise à jour
    }

    // Méthode pour supprimer une catégorie
    public boolean delete(int id) {
        String sql = "DELETE FROM category WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0; // Retourne vrai si la suppression a réussi
    }

    // Méthode privée pour vérifier si une catégorie existe en base
    private boolean categoryExists(int id) {
        String checkSql = "SELECT COUNT(*) FROM category WHERE id = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);
        return count > 0;
    }
}
