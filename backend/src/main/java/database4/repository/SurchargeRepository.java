package database4.repository;

import database4.dto.ReturnGetSurchargeOverfeeInfoDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
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

    public Map<String, Object> getOverfeeAndCash(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String checkOverfee = "SELECT overfee, cash FROM user WHERE user_id = :user_id";

        return jdbcTemplate.queryForObject(checkOverfee, namedParameters, (resultSet, i) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("overfee", resultSet.getInt("overfee"));
            map.put("cash", resultSet.getInt("cash"));
            return map;
        });
    }


    public boolean overfeePay(int overfee, String user_id) {

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("overfee", overfee);

        String sql = "UPDATE user SET overfee = overfee - :overfee, cash = cash - :overfee WHERE user_id = :user_id";
        jdbcTemplate.update(sql, namedParameters);

        return true;
    }
}
