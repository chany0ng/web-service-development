package com.database4.repository;

import com.database4.dto.PostRentalRentDto;
import com.database4.dto.PostRentalReturnDto;
import com.database4.dto.ReturnPostRentalReturnDto;
import com.database4.exceptions.RentalRentException;
import com.database4.exceptions.RentalReturnException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class RentalRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RentalRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<String> Rent(PostRentalRentDto rentalRentDto){
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bike_id", rentalRentDto.getBike_id())
                .addValue("start_location", rentalRentDto.getStart_location())
                .addValue("user_id", rentalRentDto.getUser_id());

        String checkOverfee = "SELECT overfee FROM user WHERE user_id = :user_id";
        try{
            Integer overfee = jdbcTemplate.queryForObject(checkOverfee, namedParameters, Integer.class);
            if(overfee != 0){
                throw new RentalRentException("미납금이 존재해 대여에 실패했습니다.");
            }
        } catch(EmptyResultDataAccessException e) {
            throw new RentalRentException("잘못된 사용자 ID가 전달되었습니다.");
        }

        String sql = "INSERT INTO rental(bike_id, start_location, user_id) " +
                "(SELECT :bike_id, :start_location, :user_id FROM user WHERE ticket_id IS NOT NULL AND user_id =:user_id)";
        int checkInsert = jdbcTemplate.update(sql, namedParameters);
        if(checkInsert > 0){
            String bikeUpdateSql = "UPDATE bike SET status = 'rented' WHERE  bike_id =:bike_id";
            jdbcTemplate.update(bikeUpdateSql, namedParameters);

            return Optional.of("대여에 성공했습니다.");
        } else{
            throw new RentalRentException("보유중인 이용권이 없습니다.");
        }
    }

    public Optional<ReturnPostRentalReturnDto> Return(PostRentalReturnDto postRentalReturnDto){
        String rentalUpdateSql = "UPDATE rental r JOIN bike b ON r.bike_id = b.bike_id SET end_location =:end_location, " +
                "rental_duration = CEIL(TIMESTAMPDIFF(SECOND, start_time, now())/60), " +
                "fee = CEIL(CEIL(TIMESTAMPDIFF(SECOND, start_time, now())/60) / 15) * 250, " +
                "status = 'available', location_id =:end_location " +
                "WHERE user_id =:user_id AND r.bike_id =:bike_id ORDER BY start_time DESC LIMIT 1";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("end_location", postRentalReturnDto.getEnd_location())
                .addValue("bike_id", postRentalReturnDto.getBike_id())
                .addValue("user_id", postRentalReturnDto.getUser_id());
        int checkRentalUpdate;

        try{
            checkRentalUpdate = jdbcTemplate.update(rentalUpdateSql, namedParameters);
        } catch(DataIntegrityViolationException e) {
            throw new RentalReturnException("잘못된 정보가 입력되었습니다.");
        }

        if(checkRentalUpdate > 0){
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
                            "overfee = :overfee WHERE user_id =:user_id";
                    final MapSqlParameterSource overfeeParameter = new MapSqlParameterSource()
                            .addValue("overfee", overfee)
                            .addValue("user_id", postRentalReturnDto.getUser_id());
                    jdbcTemplate.update(userUpdateSql, overfeeParameter);

                    return Optional.of(new ReturnPostRentalReturnDto(fee, cash, overfee));
                } else{
                    int remainCash = cash - (fee - price);
                    String userUpdateSql = "UPDATE user SET ticket_id = null, cash = :remainCash WHERE user_id =:user_id";
                    final MapSqlParameterSource remainCashParameter = new MapSqlParameterSource()
                            .addValue("remainCash", remainCash)
                            .addValue("user_id", postRentalReturnDto.getUser_id());
                    jdbcTemplate.update(userUpdateSql, remainCashParameter);

                    return Optional.of(new ReturnPostRentalReturnDto(fee, fee - price, 0));
                }
            } else{
                String userUpdateSql = "UPDATE user SET ticket_id = null WHERE user_id =:user_id";
                jdbcTemplate.update(userUpdateSql, namedParameters);

                return Optional.of(new ReturnPostRentalReturnDto(fee, 0, 0));
            }
        } else{
            throw new RentalReturnException("잘못된 정보가 입력되었습니다.");
        }
    }
}
