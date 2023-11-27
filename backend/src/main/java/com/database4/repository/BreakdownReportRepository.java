package database4.repository;

import database4.dto.PostBreakdownReportDto;
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

    public Optional<String> insertReport(PostBreakdownReportDto postBreakdownReportDto) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", postBreakdownReportDto.getUser_id())
                .addValue("bike_id", postBreakdownReportDto.getBike_id())
                .addValue("content", postBreakdownReportDto.getContent());

        String insertReportSql = "INSERT INTO report(user_id, bike_id, content) values(:user_id, :bike_id, :content)";
        int rowsUpdated = jdbcTemplate.update(insertReportSql, namedParameters);

        return (rowsUpdated > 0) ? Optional.of("고장신고가 접수되었습니다.") : Optional.empty();
    }

    public Optional<String> updateBikeStatus(String bikeId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bike_id", bikeId);

        String updateBikeSql = "UPDATE bike SET status = 'closed' WHERE bike_id = :bike_id";
        int rowsUpdated = jdbcTemplate.update(updateBikeSql, namedParameters);

        return (rowsUpdated > 0) ? Optional.of("자전거 상태가 업데이트되었습니다.") : Optional.empty();
    }
}
