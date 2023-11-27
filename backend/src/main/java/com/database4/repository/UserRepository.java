package database4.repository;

import database4.dto.PostUserLoginDto;
import database4.dto.PostUserJoinDto;
import database4.dto.ReturnPostUserLoginDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReturnPostUserLoginDto login(PostUserLoginDto postUserLoginDto){
        String sql = "SELECT u.user_id, u.cash, IF(r.end_location IS NULL, r.bike_id, NULL) AS bike_id, " +
                "CASE WHEN r.end_location IS NULL AND u.ticket_id IS NOT NULL " +
                "THEN TIMEDIFF(SEC_TO_TIME(t.hour * 3600),TIMEDIFF(NOW(), r.start_time)) " +
                "WHEN u.ticket_id IS NULL THEN NULL ELSE SEC_TO_TIME(t.hour * 3600) END AS remain_rental_time " +
                "FROM user u JOIN rental r ON u.user_id = r.user_id LEFT JOIN ticket t ON u.ticket_id = t.ticket_id " +
                "WHERE u.user_id = :user_id AND u.password = :password ORDER BY r.start_time DESC limit 1";

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", postUserLoginDto.getUser_id())
                .addValue("password", postUserLoginDto.getPassword());
        try{
            return jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnPostUserLoginDto.class));
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public void join(PostUserJoinDto postUserJoinDto){
        String sql = "INSERT INTO user(user_id, password, email, phone_number, pw_question, pw_answer) "
                + "SELECT :user_id, :password, :email, :phone_number, question_id, :pw_answer "
                + "FROM question WHERE content = :content";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", postUserJoinDto.getUser_id())
                .addValue("password", postUserJoinDto.getPassword())
                .addValue("email", postUserJoinDto.getEmail())
                .addValue("phone_number", postUserJoinDto.getPhone_number())
                .addValue("pw_answer", postUserJoinDto.getPw_answer())
                .addValue("content", postUserJoinDto.getContent());
        jdbcTemplate.update(sql, namedParameters);
    }
}
