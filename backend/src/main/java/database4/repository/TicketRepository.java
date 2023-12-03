package database4.repository;

import database4.dto.ReturnGetTicketInfoDto;
import database4.dto.TicketInfo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public Optional<String> getTicketIdByUserId(String userId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", userId);

        String checkTicketSql = "SELECT ticket_id FROM user WHERE user_id = :user_id";
        Integer existingTicketId = jdbcTemplate.queryForObject(checkTicketSql, namedParameters, Integer.class);
        if(existingTicketId != null){
            return Optional.empty();
        } else{
            return Optional.of("보유중인 이용권이 없습니다.");
        }
    }

    public Optional<TicketInfo> getTicketInfoByHour(int hour) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("hour", hour);

        String ticketInfoSql = "SELECT ticket_id, price FROM ticket WHERE hour = :hour";
        try {
            return Optional.of(jdbcTemplate.queryForObject(ticketInfoSql, namedParameters, new BeanPropertyRowMapper<>(TicketInfo.class)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean updateUserInfo(String userId, TicketInfo ticketInfo) {
        final MapSqlParameterSource purchaseParams = new MapSqlParameterSource()
                .addValue("ticket_id", ticketInfo.getTicketId())
                .addValue("price", ticketInfo.getPrice())
                .addValue("user_id", userId);

        String purchaseTicketSql = "UPDATE user SET ticket_id = :ticket_id, cash = cash - :price WHERE user_id = :user_id";
        int updatedRows = jdbcTemplate.update(purchaseTicketSql, purchaseParams);

        return updatedRows > 0;
    }
}