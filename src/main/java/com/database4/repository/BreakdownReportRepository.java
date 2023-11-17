package com.database4.repository;

import com.database4.dto.PostBreakdownReportDto;
import com.database4.exceptions.BreakdownReportException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BreakdownReportRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BreakdownReportRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<String> report(PostBreakdownReportDto postBreakdownReportDto){
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", postBreakdownReportDto.getUser_id())
                .addValue("bike_id", postBreakdownReportDto.getBike_id())
                .addValue("content", postBreakdownReportDto.getContent());

        try{
            String insertReportSql = "INSERT INTO report(user_id, bike_id, content) values(:user_id, :bike_id, :content)";
            int checkInsert = jdbcTemplate.update(insertReportSql, namedParameters);

            String updateBikeSql = "UPDATE bike SET status = 'closed' WHERE bike_id =:bike_id";
            jdbcTemplate.update(updateBikeSql, namedParameters);

            return Optional.of("고장신고가 접수되었습니다.");
        } catch (DataIntegrityViolationException e) {
            throw new BreakdownReportException("잘못된 정보가 입력되었습니다.");
        }
    }
}
