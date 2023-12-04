package db.project.repository;

import db.project.dto.PostRentalRentDto;
import db.project.dto.PostRentalReturnDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class RentalRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RentalRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<String> checkOverfee(String userId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", userId);

        String checkOverfeeSql = "SELECT overfee FROM user WHERE user_id = :user_id";
        try {
            Integer overfee = jdbcTemplate.queryForObject(checkOverfeeSql, namedParameters, Integer.class);
            if(overfee != 0) {
                return Optional.empty();
            } else{
                return Optional.of("미납금 없음");
            }
        } catch (EmptyResultDataAccessException e) {
            return Optional.of("잘못된 사용자 ID가 전달되었습니다.");
        }
    }

    public Optional<String> insertRental(PostRentalRentDto rentalRentDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bike_id", rentalRentDto.getBike_id())
                .addValue("start_location", rentalRentDto.getStart_location())
                .addValue("user_id", user_id);

        String insertRentalSql = "INSERT INTO rental(bike_id, start_location, user_id) " +
                "(SELECT :bike_id, :start_location, :user_id FROM user WHERE ticket_id IS NOT NULL AND user_id = :user_id)";
        int checkInsert = jdbcTemplate.update(insertRentalSql, namedParameters);
        return (checkInsert > 0) ? Optional.of("rental 성공") : Optional.empty();
    }

    public Optional<String> updateBikeStatus(String bikeId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bike_id", bikeId);

        String updateBikeSql = "UPDATE bike SET status = 'rented' WHERE  bike_id = :bike_id";
        int rowsUpdated = jdbcTemplate.update(updateBikeSql, namedParameters);

        return (rowsUpdated > 0) ? Optional.of("자전거 상태 업데이트 성공") : Optional.empty();
    }

    public int updateRental(PostRentalReturnDto postRentalReturnDto, String user_id) {
        String rentalUpdateSql = "UPDATE rental r JOIN bike b ON r.bike_id = b.bike_id SET end_location = :end_location, " +
                "rental_duration = CEIL(TIMESTAMPDIFF(SECOND, start_time, now())/60), " +
                "fee = CEIL(CEIL(TIMESTAMPDIFF(SECOND, start_time, now())/60) / 15) * 250, " +
                "status = 'available', location_id = :end_location " +
                "WHERE user_id = :user_id AND r.bike_id = :bike_id ORDER BY start_time DESC LIMIT 1";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("end_location", postRentalReturnDto.getEnd_location())
                .addValue("bike_id", postRentalReturnDto.getBike_id())
                .addValue("user_id", user_id);
        return jdbcTemplate.update(rentalUpdateSql, namedParameters);
    }

    public Map<String, Object> getRentalValues(String bike_id, String user_id) {
        String valueSelectSql = "SELECT fee, price, cash FROM rental r JOIN user u ON r.user_id = u.user_id " +
                "JOIN ticket t ON t.ticket_id = u.ticket_id " +
                "WHERE u.user_id = :user_id AND bike_id = :bike_id ORDER BY start_time DESC LIMIT 1";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("bike_id", bike_id);
        return jdbcTemplate.queryForObject(valueSelectSql, namedParameters, (resultSet, i) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("price", resultSet.getInt("price"));
            map.put("cash", resultSet.getInt("cash"));
            map.put("fee", resultSet.getInt("fee"));
            return map;
        });
    }

    public int updateUserData(String userId, int overfee, int remainCash) {
        String userUpdateSql = "UPDATE user SET ticket_id = null, cash =:remainCash, " +
                "overfee =:overfee WHERE user_id =:userId";
        MapSqlParameterSource overfeeParameter = new MapSqlParameterSource()
                .addValue("overfee", overfee)
                .addValue("userId", userId)
                .addValue("remainCash", remainCash);
        return jdbcTemplate.update(userUpdateSql, overfeeParameter);
    }
}
