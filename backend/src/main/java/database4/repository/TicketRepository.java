package database4.repository;

import database4.dto.ReturnGetTicketInfoDto;
import database4.dto.TicketInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TicketRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TicketRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReturnGetTicketInfoDto> ticketList(){
        String sql = "SELECT hour, price FROM ticket";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetTicketInfoDto.class));
    }

    public Map<String, Object> getCashAndTicketIdByUserId(String userId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", userId);

        String sql = "SELECT cash, ticket_id FROM user WHERE user_id = :user_id";
        return jdbcTemplate.queryForObject(sql, namedParameters, (resultSet, i) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("cash", resultSet.getInt("cash"));
            map.put("ticket_id", resultSet.getInt("ticket_id"));
            return map;
        });
    }

    public Optional<TicketInfo> getTicketInfoByHour(int hour) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("hour", hour);

        String sql = "SELECT ticket_id, price FROM ticket WHERE hour = :hour";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(TicketInfo.class)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updatePurchaseUserInfo(String userId, TicketInfo ticketInfo) {
        final MapSqlParameterSource purchaseParams = new MapSqlParameterSource()
                .addValue("ticket_id", ticketInfo.getTicketId())
                .addValue("price", ticketInfo.getPrice())
                .addValue("user_id", userId);

        String sql = "UPDATE user SET ticket_id = :ticket_id, cash = cash - :price WHERE user_id = :user_id";
        jdbcTemplate.update(sql, purchaseParams);
    }

    public Optional<String> getTicketIdByPhoneNumber(String phone_number) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("phone_number", phone_number);

        String sql = "SELECT ticket_id FROM user WHERE phone_number = :phone_number";
        Integer existingTicketId = jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
        if(existingTicketId != null){
            return Optional.empty();
        } else{
            return Optional.of("보유중인 이용권이 없습니다.");
        }
    }

    public int getCashByUserId(String userId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", userId);

        String sql = "SELECT cash FROM user WHERE user_id = :user_id";
        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public void updateGiftGiverInfo(String user_id, int price) {
        final MapSqlParameterSource purchaseParams = new MapSqlParameterSource()
                .addValue("price", price)
                .addValue("user_id", user_id);

        String sql = "UPDATE user SET cash = cash - :price WHERE user_id = :user_id";
        jdbcTemplate.update(sql, purchaseParams);
    }

    public void updateGiftReceiverInfo(String phone_number, int ticket_id) {
        final MapSqlParameterSource purchaseParams = new MapSqlParameterSource()
                .addValue("ticket_id", ticket_id)
                .addValue("phone_number", phone_number);

        String sql = "UPDATE user SET ticket_id = :ticket_id WHERE phone_number = :phone_number";
        jdbcTemplate.update(sql, purchaseParams);
    }
}