package com.database4.repository;

import com.database4.dto.PostRentalRentDto;
import com.database4.dto.PostRentalReturnDto;
import com.database4.dto.ReturnPostRentalReturnDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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
            String bikeUpdateSql = "UPDATE bike SET status = 'rented' WHERE  bike_id =:bike_id";
            jdbcTemplate.update(bikeUpdateSql, namedParameters);

            return true;
        } else{
            return false;
        }
    }

    public ReturnPostRentalReturnDto Return(PostRentalReturnDto postRentalReturnDto){
        String rentalUpdateSql = "UPDATE rental SET end_location = :end_location, " +
                "rental_duration = CEIL((TIME_TO_SEC(now()) - TIME_TO_SEC(start_time))/60), fee = CEIL(rental_duration / 15) * 250 " +
                "WHERE user_id =:user_id AND bike_id =:bike_id ORDER BY start_time desc limit 1";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("end_location", postRentalReturnDto.getEnd_location())
                .addValue("bike_id", postRentalReturnDto.getBike_id())
                .addValue("user_id", postRentalReturnDto.getUser_id());
        int checkRentalUpdate = jdbcTemplate.update(rentalUpdateSql, namedParameters);

        if(checkRentalUpdate == 1){
            String valueSelectSql = "SELECT fee, price, cash FROM rental r JOIN user u ON r.user_id = u.user_id " +
                    "JOIN ticket t ON t.ticket_id = u.ticket_id " +
                    "WHERE u.user_id =:user_id AND bike_id =:bike_id ORDER BY start_time DESC LIMIT 1";
            Map<String, Object> value = jdbcTemplate.queryForObject(valueSelectSql, namedParameters, (resultSet, i) -> {
                Map<String, Object> map = new HashMap<>();
                map.put("price", resultSet.getInt("price"));
                map.put("cash", resultSet.getInt("cash"));
                map.put("fee", resultSet.getInt("fee"));
                return map;
            });
            int price = (int) value.get("price");
            int cash = (int) value.get("cash");
            int fee = (int) value.get("fee");

            if(fee > price){
                int overfee = fee > cash + price ? (fee - price) - cash : 0;
                if(overfee > 0){
                    String userUpdateSql = "UPDATE user SET ticket_id = null, cash = 0, " +
                            "overfee = " + overfee + " WHERE user_id =:user_id";
                    jdbcTemplate.update(userUpdateSql, namedParameters);

                    return new ReturnPostRentalReturnDto(fee, cash, overfee);
                } else{
                    int remainCash = cash - (fee - price);
                    String userUpdateSql = "UPDATE user SET ticket_id = null, cash = " + (cash - (fee - price)) +
                            " WHERE user_id =:user_id";
                    jdbcTemplate.update(userUpdateSql, namedParameters);

                    return new ReturnPostRentalReturnDto(fee, fee - price, 0);
                }
            } else{
                String userUpdateSql = "UPDATE user SET ticket_id = null WHERE user_id =:user_id";
                jdbcTemplate.update(userUpdateSql, namedParameters);

                return new ReturnPostRentalReturnDto(fee, 0, 0);
            }
        } else{
            return null;
        }
    }
}
