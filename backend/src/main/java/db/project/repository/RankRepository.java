package db.project.repository;

import db.project.dto.RankDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RankRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RankRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RankDto.RankTime> allUserTimeRank() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY SUM(rental_duration) DESC) AS ranking, user_id, " +
                "SUM(rental_duration) AS duration_time FROM rental WHERE end_location IS NOT NULL GROUP BY user_id";

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(RankDto.RankTime.class));
    }

    public Optional<RankDto.RankTime> userTimeRank(String user_id) {
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

    public List<RankDto.RankCount> allUsingCountRank() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY COUNT(user_id) DESC) AS ranking, user_id, " +
                "COUNT(user_id) AS using_count FROM rental WHERE end_location IS NOT NULL GROUP BY user_id";

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(RankDto.RankCount.class));
    }

    public Optional<RankDto.RankCount> userUsingCountRank(String user_id) {
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
