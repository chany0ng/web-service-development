package db.project.repository;

import db.project.domain.Admin;
import db.project.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Optional<User> findUserById(String id) {
        String sql = "SELECT user_id FROM USER WHERE USER_ID= :id";
        try {
            SqlParameterSource param = new MapSqlParameterSource("id", id);
            User user = namedParameterJdbcTemplate.queryForObject(sql, param, userMapper);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public String save(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String sql = """
        INSERT INTO user (user_id, password, email, phone_number, pw_question, pw_answer, role, ticket_id)
        VALUES (:user_id, :password, :email, :phone_number, :pw_question, :pw_answer, :role, :ticket_id)        
    """;
        System.out.println(user.getPassword());
        SqlParameterSource parameterSource = new MapSqlParameterSource("user_id", user.getId())
                .addValue("password", bCryptPasswordEncoder.encode(user.getPassword()))
                .addValue("email", user.getEmail())
                .addValue("phone_number", user.getPhone_number())
                .addValue("pw_question", user.getPw_question())
                .addValue("pw_answer", user.getPw_answer())
                .addValue("role", user.getRole())
                .addValue("ticket_id", user.getTicket_id());
        int affctedRows = namedParameterJdbcTemplate.update(sql, parameterSource);
        return user.getId();
    }


    private final RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = User.builder()
                .id(rs.getString("user_id"))
                .password(rs.getString("password"))
                .role(rs.getString("role"))
                .build();
        return user;
    };
}
