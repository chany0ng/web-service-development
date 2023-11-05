package db.project.repository;


import db.project.domain.Admin;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<Admin> findList() {
        String sql = "select * from admin";
        return namedParameterJdbcTemplate.query(sql, EmptySqlParameterSource.INSTANCE, adminMapper);
    }

    public Optional<Admin> findAdminByAdminId(String adminId) {
        String sql = "SELECT * FROM ADMIN WHERE ADMIN_ID= :adminId";
        try {
            SqlParameterSource param = new MapSqlParameterSource("adminId", adminId);
            Admin admin = namedParameterJdbcTemplate.queryForObject(sql, param, adminMapper);
            return Optional.of(admin);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        
        
    }

    private final RowMapper<Admin> adminMapper = (rs, rowNum) -> {
            Admin admin = new Admin();
            admin.setAdmin_id(rs.getString("admin_id"));
            admin.setPassword(rs.getString("password"));
            return admin;
        };
}
