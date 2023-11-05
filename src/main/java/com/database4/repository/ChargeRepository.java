package com.database4.repository;

import com.database4.dto.PostChargeDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChargeRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public ChargeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void charge(PostChargeDto postChargeDto){
        String sql = "UPDATE user SET cash = cash + :cash Where user_id = :user_id";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", postChargeDto.getUser_id())
                .addValue("cash", postChargeDto.getCash());
        jdbcTemplate.update(sql, namedParameters);
    }
}
