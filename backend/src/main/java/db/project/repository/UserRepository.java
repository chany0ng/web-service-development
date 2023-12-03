package db.project.repository;

import db.project.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<User> findUserById(String id) {
        try {
            String sql = "SELECT * FROM USER WHERE USER_ID= :id";
            SqlParameterSource param = new MapSqlParameterSource("id", id);
            User user = namedParameterJdbcTemplate.queryForObject(sql, param, userMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> save(User user) {
        try {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String sql = """
                        INSERT INTO user (user_id, password, email, phone_number, pw_question, pw_answer, role)
                        VALUES (:user_id, :password, :email, :phone_number, :pw_question, :pw_answer, :role)        
                    """;
            SqlParameterSource parameterSource = new MapSqlParameterSource("user_id", user.getId())
                    .addValue("password", bCryptPasswordEncoder.encode(user.getPassword()))
                    .addValue("email", user.getEmail())
                    .addValue("phone_number", user.getPhone_number())
                    .addValue("pw_question", user.getPw_question())
                    .addValue("pw_answer", user.getPw_answer())
                    .addValue("role", user.getRole());
            int affctedRows = namedParameterJdbcTemplate.update(sql, parameterSource);

            return Optional.of(user);
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }

    }

    public Optional<String> findPWQuestionById(String id) {
        try {
            String sql = """
                    SELECT content FROM USER AS A JOIN QUESTION AS B ON A.PW_QUESTION=B.QUESTION_ID
                    WHERE USER_ID= :id
                 """;
            SqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
            RowMapper<String> questionMapper = (rs, rowNum) -> {
                return rs.getString("content");
            };
            String question = namedParameterJdbcTemplate.queryForObject(sql, parameterSource, questionMapper);
            return Optional.of(question);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updatePW(String id, String password) {
        String sql = "UPDATE USER SET password= :password WHERE user_id= :id";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id)
                .addValue("password", bCryptPasswordEncoder.encode(password));
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    private final RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = User.builder()
                .id(rs.getString("user_id"))
                .password(rs.getString("password"))
                .role(rs.getString("role"))
                .pw_answer(rs.getString("pw_answer"))
                .build();
        return user;
    };

}
