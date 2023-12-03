package database4.repository;

import database4.dto.ReturnGetSurchargeOverfeeInfoDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SurchargeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SurchargeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<ReturnGetSurchargeOverfeeInfoDto> overfeeInfo(String user_id){
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);

        String sql = "SELECT overfee FROM user WHERE user_id = :user_id";

        try{
            ReturnGetSurchargeOverfeeInfoDto overfee = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetSurchargeOverfeeInfoDto.class));
            return Optional.of(overfee);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public int getOverfee(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String checkOverfee = "SELECT overfee FROM user WHERE user_id = :user_id";

        return jdbcTemplate.queryForObject(checkOverfee, namedParameters, Integer.class);
    }

    public boolean overfeePay(int cash, String user_id) {

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("cash", cash);

        String sql = "UPDATE user SET overfee = overfee - :cash WHERE user_id = :user_id";
        jdbcTemplate.update(sql, namedParameters);

        return true;
    }
}
