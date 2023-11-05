package com.database4.repository;

import com.database4.dto.PostTicketPurchaseDto;
import com.database4.dto.ReturnGetTicketPurchaseDto;
import com.database4.dto.ReturnPostUserLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class TicketRepository {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public TicketRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReturnGetTicketPurchaseDto> ticketList(){
        String sql = "SELECT hour FROM ticket";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetTicketPurchaseDto.class));
    }

    public int purchase(PostTicketPurchaseDto postTicketPurchaseDto){
        int prevCash = postTicketPurchaseDto.getCash();

        String sql = "UPDATE user u JOIN (SELECT ticket_id FROM ticket WHERE hour = :hour) AS t " +
                "SET u.ticket_id = CASE WHEN u.cash > (1000 * t.ticket_id) THEN t.ticket_id ELSE u.ticket_id END, " +
                "u.cash = CASE WHEN u.cash > (1000 * t.ticket_id) THEN u.cash - (1000 * t.ticket_id) ELSE u.cash END " +
                "WHERE u.user_id = :user_id AND u.ticket_id IS NULL";
        final MapSqlParameterSource namedParameters  = new MapSqlParameterSource()
                .addValue("user_id", postTicketPurchaseDto.getUser_id())
                .addValue("hour", postTicketPurchaseDto.getHour());

        int updatedRows = jdbcTemplate.update(sql, namedParameters);
        if(updatedRows > 0){
            String checkCashSql = "SELECT cash FROM user WHERE user_id = :user_id";
            int curCash = jdbcTemplate.queryForObject(checkCashSql, namedParameters, Integer.class);
            if(curCash != prevCash){
                return 1;
            } else{
                return -1;
            }
        } else{
            return 0;
        }
    }
}
