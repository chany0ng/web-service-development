package com.database4.repository;

import com.database4.dto.PostTicketPurchaseDto;
import com.database4.dto.ReturnGetTicketInfoDto;
import com.database4.dto.TicketInfo;
import com.database4.exceptions.TicketPurchaseException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Optional<String> purchase(PostTicketPurchaseDto postTicketPurchaseDto){
        int prevCash = postTicketPurchaseDto.getCash();

        final MapSqlParameterSource namedParameters  = new MapSqlParameterSource()
                .addValue("user_id", postTicketPurchaseDto.getUser_id())
                .addValue("hour", postTicketPurchaseDto.getHour());

        String checkTicketSql = "SELECT ticket_id FROM user WHERE user_id = :user_id";
        try{
            Integer existingTicketId = jdbcTemplate.queryForObject(checkTicketSql, namedParameters, Integer.class);
            if(existingTicketId != null){
                throw new TicketPurchaseException("이미 이용권을 보유 중입니다.");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new TicketPurchaseException("잘못된 사용자 ID가 전달되었습니다.");
        }

        String ticketInfoSql = "SELECT ticket_id, price FROM ticket WHERE hour = :hour";
        TicketInfo ticketInfo;
        try{
            ticketInfo = jdbcTemplate.queryForObject(ticketInfoSql, namedParameters, new BeanPropertyRowMapper<>(TicketInfo.class));
        } catch (EmptyResultDataAccessException e) {
            throw new TicketPurchaseException("잘못된 시간 정보가 전달되었습니다.");
        }

        if(ticketInfo.getPrice() > prevCash){
            throw new TicketPurchaseException("소지금이 부족합니다.");
        }

        String purchaseTicketSql = "UPDATE user SET ticket_id = :ticket_id, cash = cash - :price WHERE user_id = :user_id";
        final MapSqlParameterSource purchaseParams = new MapSqlParameterSource()
                .addValue("ticket_id", ticketInfo.getTicketId())
                .addValue("price", ticketInfo.getPrice())
                .addValue("user_id", postTicketPurchaseDto.getUser_id());
        int updatedRows = jdbcTemplate.update(purchaseTicketSql, purchaseParams);

        return Optional.of("이용권 구매에 성공했습니다.");
    }
}
