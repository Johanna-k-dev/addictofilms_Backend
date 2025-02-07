package com.visiotech.addictofilms.daos;

import com.visiotech.addictofilms.exeptions.ResourceNotFoundException;
import com.visiotech.addictofilms.entity.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getLong("id_user"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("role")
    );

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM `user`";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM `user` WHERE email = ?";
        return jdbcTemplate.query(sql, userRowMapper, email)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("l'utilisateur avec l'EMAIL : " + email + " n'existe pas"));
    }

    public boolean save(User user) {
        String sql = "INSERT INTO `user` (email, password, role) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getRole());
        return rowsAffected > 0;
    }

    public User update(String email, User user) {
        if (!userExists(email)) {
            throw new ResourceNotFoundException("L'utilisateur avec l'EMAIL : " + email + " n'existe pas");
        }

        String sql = "UPDATE `user` SET email = ?, password = ? WHERE email = ?";
        int rowsAffected = jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), email);

        if (rowsAffected <= 0) {
            throw new ResourceNotFoundException("Échec de la mise à jour du produit avec l'EMAIL : " + email);
        }

        return this.findByEmail(user.getEmail());
    }


    private boolean userExists(String email) {
        String checkSql = "SELECT COUNT(*) FROM `user` WHERE email = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, email);
        return count > 0;
    }

    public boolean delete(String email) {
        String sql = "DELETE FROM `user` WHERE email = ?";
        int rowsAffected = jdbcTemplate.update(sql, email);
        return rowsAffected > 0;
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email) > 0;
    }


}
