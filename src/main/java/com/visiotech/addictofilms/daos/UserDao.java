package com.visiotech.addictofilms.daos;

import com.visiotech.addictofilms.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDao {

    static RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("password")
    );

    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.query(sql, userRowMapper, email)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("l'utilisateur avec l'EMAIL : " + email + " n'existe pas"));
    }

    public User save(User user) {
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getPassword());

        String sqlGetId = "SELECT LAST_INSERT_ID()";
        int userId = jdbcTemplate.queryForObject(sqlGetId, Integer.class);  // Correction ici

        user.setId(userId);
        return user;
    }


    public User update(String email, User user) {
        if (!userExists(email)) {
            throw new RuntimeException("L'utilisateur avec l'EMAIL : " + email + " n'existe pas");
        }

        String sql = "UPDATE users SET email = ?, password = ? WHERE email = ?";
        int rowsAffected = jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), email);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour du produit avec l'EMAIL : " + email);
        }

        return this.findByEmail(email);
    }

    // méthode utilitaire à mettre en bas du fichier
    private boolean userExists(String email) {
        String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class,email);
        return count > 0;
    }

    public boolean delete(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        int rowsAffected = jdbcTemplate.update(sql, email);
        return rowsAffected > 0;
    }
}
