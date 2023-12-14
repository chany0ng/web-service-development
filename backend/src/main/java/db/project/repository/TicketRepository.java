package db.project.repository;

import db.project.dto.ReturnGetTicketInfoDto;
import db.project.dto.TicketInfo;
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

    public List<ReturnGetTicketInfoDto> findHourAndPrice(){
        String sql = "SELECT hour, price FROM ticket";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetTicketInfoDto.class));
    }

    public TicketInfo findIdAndPriceByHour(int hour) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("hour", hour);

        String sql = "SELECT ticket_id, price FROM ticket WHERE hour = :hour";

        return jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(TicketInfo.class));
    }
}