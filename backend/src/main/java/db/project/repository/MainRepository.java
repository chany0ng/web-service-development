package db.project.repository;

import db.project.dto.ReturnGetMainDto;
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

    public ReturnGetMainDto findUserInfoNeedForMain(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String sql = "SELECT u.user_id, email, phone_number, cash, IF(u.ticket_id IS NULL, 0, hour) AS hour, " +
                "IF(r.user_id IS NOT NULL AND end_location IS NULL, TRUE, FALSE) AS isRented, IF(r.user_id IS NOT NULL AND end_location IS NULL, bike_id, NULL) AS bike_id " +
                "FROM user u LEFT JOIN ticket t ON u.ticket_id = t.ticket_id " +
                "LEFT JOIN rental r ON u.user_id = r.user_id WHERE u.user_id =:user_id";

        ReturnGetMainDto returnGetMainDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetMainDto.class));
        return returnGetMainDto;
    }
}
