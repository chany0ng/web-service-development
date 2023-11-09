package com.database4.repository;

import com.database4.dto.PostRentalRentDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class RentalRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RentalRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean Rent(PostRentalRentDto rentalRentDto){
        String sql = "INSERT INTO rental(bike_id, start_location, user_id) " +
                "(SELECT :bike_id, :start_location, :user_id FROM user WHERE ticket_id IS NOT NULL AND user_id =:user_id)";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bike_id", rentalRentDto.getBike_id())
                .addValue("start_location", rentalRentDto.getStart_location())
                .addValue("user_id", rentalRentDto.getUser_id());

        int checkInsert = jdbcTemplate.update(sql, namedParameters);
        if(checkInsert > 0){
            return true;
        } else{
            return false;
        }
    }
}
