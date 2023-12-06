package db.project.repository;

import db.project.dto.ReturnGetAdminMainDto;
import db.project.dto.ReturnGetUserMainDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MainRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MainRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReturnGetUserMainDto userMain(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String sql = "SELECT u.user_id, email, phone_number, cash, IF(u.ticket_id IS NULL, 0, hour) AS hour, " +
                "IF(r.user_id IS NOT NULL AND end_location IS NULL, TRUE, FALSE) AS isRented, IF(r.user_id IS NOT NULL AND end_location IS NULL, bike_id, NULL) AS bike_id " +
                "FROM user u LEFT JOIN ticket t ON u.ticket_id = t.ticket_id " +
                "LEFT JOIN rental r ON u.user_id = r.user_id WHERE u.user_id =:user_id LIMIT 1";

        ReturnGetUserMainDto returnGetUserMainDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetUserMainDto.class));
        return returnGetUserMainDto;
    }

    public ReturnGetAdminMainDto adminMain(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String sql = "SELECT :user_id AS user_id, COUNT(report_id) AS report FROM report WHERE status = 'received'";

        ReturnGetAdminMainDto returnGetAdminMainDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetAdminMainDto.class));
        return returnGetAdminMainDto;
    }
}
