package db.project.repository;

import db.project.dto.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RentalRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RentalRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createRental(PostRentalRentDto rentalRentDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bike_id", rentalRentDto.getBike_id())
                .addValue("start_location", rentalRentDto.getStart_location())
                .addValue("user_id", user_id);
        String insertRentalSql = "INSERT INTO rental(bike_id, start_location, user_id) " +
                "values(:bike_id, :start_location, :user_id)";

        jdbcTemplate.update(insertRentalSql, namedParameters);
    }

    public int updateRentalByUserAndBike(PostRentalReturnDto postRentalReturnDto, String user_id) {
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

    public Map<String, Object> findRentalByUserAndBike(String bike_id, String user_id) {
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

    public List<ReturnPostRentalHistoryDto> findRentalHistory(PostRentalHistoryDto postRentalHistoryDto, String user_id) {

        String endDate = LocalDate.parse(postRentalHistoryDto.getEnd_date()).plusDays(1).toString();

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("start_date", postRentalHistoryDto.getStart_date())
                .addValue("end_date", endDate)
                .addValue("user_id", user_id);

        String sql = "SELECT bike_id, start_time, start_location, end_time, end_location FROM rental " +
                "WHERE user_id =:user_id AND start_time BETWEEN DATE(:start_date) AND DATE(:end_date) " +
                "AND end_location IS NOT NULL ORDER BY start_time";

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnPostRentalHistoryDto.class));
    }

    public List<RankDto.RankTime> findAllUserTimeRank() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY SUM(rental_duration) DESC) AS ranking, user_id, " +
                "SUM(rental_duration) AS duration_time FROM rental WHERE end_location IS NOT NULL GROUP BY user_id";

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(RankDto.RankTime.class));
    }

    public Optional<RankDto.RankTime> findUserTimeRank(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String sql = "SELECT ranking, user_id, duration_time FROM" +
                "(SELECT ROW_NUMBER() OVER (ORDER BY SUM(rental_duration) DESC) AS ranking, user_id, SUM(rental_duration) AS duration_time " +
                "FROM rental WHERE end_location IS NOT NULL GROUP BY user_id) AS t WHERE user_id =:user_id";

        try{
            RankDto.RankTime rankTimeDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(RankDto.RankTime.class));
            return Optional.of(rankTimeDto);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<RankDto.RankCount> findAllUserCountRank() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY COUNT(user_id) DESC) AS ranking, user_id, " +
                "COUNT(user_id) AS using_count FROM rental WHERE end_location IS NOT NULL GROUP BY user_id";

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(RankDto.RankCount.class));
    }

    public Optional<RankDto.RankCount> findUserCountRank(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String sql = "SELECT ranking, user_id, using_count FROM" +
                "(SELECT ROW_NUMBER() OVER (ORDER BY COUNT(user_id) DESC) AS ranking, user_id, COUNT(user_id) AS using_count " +
                "FROM rental WHERE end_location IS NOT NULL GROUP BY user_id) AS t WHERE user_id =:user_id";

        try{
            RankDto.RankCount rankCountDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(RankDto.RankCount.class));
            return Optional.of(rankCountDto);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
