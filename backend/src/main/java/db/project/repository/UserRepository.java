package db.project.repository;

import db.project.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

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

    public Optional<String> findTicketById(String receivedUser_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", receivedUser_id);

        String sql = "SELECT ticket_id FROM user WHERE user_id = :user_id";
        Integer existingTicketId = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
        if(existingTicketId != null){
            return Optional.empty();
        } else{
            return Optional.of("보유중인 이용권이 없습니다.");
        }
    }

    public int findCashById(String userId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", userId);

        String sql = "SELECT cash FROM user WHERE user_id = :user_id";
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public int findOverfeeById(String user_id){
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);

        String sql = "SELECT overfee FROM user WHERE user_id = :user_id";

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public int findUserCountByRole() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT COUNT(user_id) userCount FROM user WHERE role = 'user'";

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Map<String, Object> findCashAndTicketById(String userId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", userId);

        String sql = "SELECT cash, ticket_id FROM user WHERE user_id = :user_id";
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, (resultSet, i) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("cash", resultSet.getInt("cash"));
            map.put("ticket_id", resultSet.getInt("ticket_id"));
            return map;
        });
    }

    public Map<String, Object> findOverfeeAndCashById(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String checkOverfee = "SELECT overfee, cash FROM user WHERE user_id = :user_id";

        return namedParameterJdbcTemplate.queryForObject(checkOverfee, namedParameters, (resultSet, i) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("overfee", resultSet.getInt("overfee"));
            map.put("cash", resultSet.getInt("cash"));
            return map;
        });
    }

    public UserOverfeeAndTicketDto findOverfeeAndTicketById(String user_id){
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);

        String sql = "SELECT overfee, ticket_id FROM user WHERE user_id = :user_id";

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(UserOverfeeAndTicketDto.class));
    }

    public int findUserCountByIdAndRole(PostUserInfoListDto postUserInfoListDto) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", "%" + postUserInfoListDto.getUser_id() + "%");
        String sql = "SELECT COUNT(user_id) userCount FROM user WHERE user_id LIKE :user_id AND role = 'user'";

        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Optional<List<ReturnGetUserInfoListDto>> findUserInfoByRole(int page) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page);
        String sql = "SELECT user_id, phone_number, email FROM user WHERE role = 'user' LIMIT :page, 10";

        try {
            List<ReturnGetUserInfoListDto> userInfoListDto = namedParameterJdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetUserInfoListDto.class));
            return Optional.of(userInfoListDto);
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public Optional<List<ReturnGetUserInfoListDto>> findUserInfoByIdAndRole(int page, PostUserInfoListDto postUserInfoListDto) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page)
                .addValue("user_id", "%" + postUserInfoListDto.getUser_id() + "%");
        String sql = "SELECT user_id, phone_number, email FROM user WHERE user_id LIKE :user_id AND role = 'user' LIMIT :page, 10";

        try {
            List<ReturnGetUserInfoListDto> userInfoListDto = namedParameterJdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetUserInfoListDto.class));
            return Optional.of(userInfoListDto);
        } catch (BadSqlGrammarException e) {
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

    public int updatePW(String id, String password) {
        String sql = "UPDATE USER SET password= :password WHERE user_id= :id";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id)
                .addValue("password", bCryptPasswordEncoder.encode(password));
        return namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public int updateUser(String id, String email, String phone_number) {
        String sql = "UPDATE USER SET email= :email, phone_number= :phone_number WHERE user_id= :id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id)
                .addValue("email", email)
                .addValue("phone_number", phone_number);
        return namedParameterJdbcTemplate.update(sql, sqlParameterSource);
    }

    public void updateCashById(String user_id, int cash) {
        final MapSqlParameterSource purchaseParams = new MapSqlParameterSource()
                .addValue("cash", cash)
                .addValue("user_id", user_id);

        String sql = "UPDATE user SET cash = cash + :cash WHERE user_id = :user_id";
        namedParameterJdbcTemplate.update(sql, purchaseParams);
    }

    public void updateTicketById(String user_id, int ticket_id) {
        final MapSqlParameterSource purchaseParams = new MapSqlParameterSource()
                .addValue("ticket_id", ticket_id)
                .addValue("user_id", user_id);

        String sql = "UPDATE user SET ticket_id = :ticket_id WHERE user_id = :user_id";
        namedParameterJdbcTemplate.update(sql, purchaseParams);
    }

    public void updateCashAndTicketById(String userId, TicketInfo ticketInfo) {
        final MapSqlParameterSource purchaseParams = new MapSqlParameterSource()
                .addValue("ticket_id", ticketInfo.getTicketId())
                .addValue("price", ticketInfo.getPrice())
                .addValue("user_id", userId);

        String sql = "UPDATE user SET ticket_id = :ticket_id, cash = cash - :price WHERE user_id = :user_id";
        namedParameterJdbcTemplate.update(sql, purchaseParams);
    }

    public boolean updateCashAndOverfeeById(int overfee, String user_id) {

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("overfee", overfee);

        String sql = "UPDATE user SET overfee = overfee - :overfee, cash = cash - :overfee WHERE user_id = :user_id";
        namedParameterJdbcTemplate.update(sql, namedParameters);

        return true;
    }

    public int updateCashAndTicketAndOverfeeById(String userId, int overfee, int remainCash) {
        String userUpdateSql = "UPDATE user SET ticket_id = null, cash =:remainCash, " +
                "overfee =:overfee WHERE user_id =:userId";
        MapSqlParameterSource overfeeParameter = new MapSqlParameterSource()
                .addValue("overfee", overfee)
                .addValue("userId", userId)
                .addValue("remainCash", remainCash);
        return namedParameterJdbcTemplate.update(userUpdateSql, overfeeParameter);
    }

    public ReturnGetUserMainDto userMain(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String sql = "SELECT u.user_id, email, phone_number, cash, overfee, IF(u.ticket_id IS NULL, 0, hour) AS hour, " +
                "IF(end_location IS NULL, 1, 0) AS isRented, IF(end_location IS NULL, bike_id, NULL) AS bike_id " +
                "FROM user u LEFT JOIN ticket t ON u.ticket_id = t.ticket_id " +
                "LEFT JOIN rental r ON u.user_id = r.user_id WHERE u.user_id =:user_id ORDER BY start_time DESC LIMIT 1";

        ReturnGetUserMainDto returnGetUserMainDto = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetUserMainDto.class));
        return returnGetUserMainDto;
    }

    private final RowMapper<User> userMapper = (rs, rowNum) -> {
        User user = User.builder()
                .id(rs.getString("user_id"))
                .password(rs.getString("password"))
                .role(rs.getString("role"))
                .pw_question(rs.getInt("pw_question"))
                .pw_answer(rs.getString("pw_answer"))
                .build();
        return user;
    };

}
