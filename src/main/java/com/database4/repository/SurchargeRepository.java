package com.database4.repository;

import com.database4.dto.PostSurchargeInfoDto;
import com.database4.dto.PostSurchargePayDto;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Optional<Integer> overfeeInfo(PostSurchargeInfoDto postSurchargeInfoDto){
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", postSurchargeInfoDto.getUser_id());

        String sql = "SELECT overfee FROM user WHERE user_id = :user_id";

        try{
            Integer overfee = jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
            return Optional.of(overfee);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public boolean overfeePay(PostSurchargePayDto postSurchargePayDto){
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", postSurchargePayDto.getUser_id())
                .addValue("cash", postSurchargePayDto.getCash());

        String checkOverfee = "SELECT overfee FROM user WHERE user_id = :user_id";
        Integer overfee = jdbcTemplate.queryForObject(checkOverfee, namedParameters, Integer.class);

        if(postSurchargePayDto.getCash() > overfee){
            return false;
        }
        String sql = "UPDATE user SET overfee = overfee -:cash WHERE user_id =:user_id";

        jdbcTemplate.update(sql, namedParameters);

        return true;
    }
}
