package db.project.repository;

import db.project.dto.PostBreakdownReportDto;
import db.project.dto.PostBreakdownReportRepairDto;
import db.project.dto.ReturnGetBreakdownReportListDto;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BreakdownReportRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BreakdownReportRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<String> insertReport(PostBreakdownReportDto postBreakdownReportDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("bike_id", postBreakdownReportDto.getBike_id())
                .addValue("tire", postBreakdownReportDto.getTire())
                .addValue("chain", postBreakdownReportDto.getChain())
                .addValue("saddle", postBreakdownReportDto.getSaddle())
                .addValue("pedal", postBreakdownReportDto.getPedal())
                .addValue("terminal", postBreakdownReportDto.getTerminal());

        String insertReportSql = "INSERT INTO report(user_id, bike_id, tire, chain, saddle, pedal, terminal) " +
                "values(:user_id, :bike_id, :tire, :chain, :saddle, :pedal, :terminal)";
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

    public int getReportCount() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT count(report_id) reportCount FROM report WHERE status = 'received'";

        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Optional<List<ReturnGetBreakdownReportListDto>> reportList(int page) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page);
        String sql = "SELECT user_id, tire, chain, saddle, pedal, terminal, created_at, bike_id, status FROM report " +
                "WHERE status = 'received' LIMIT :page, 10";

        try {
            List<ReturnGetBreakdownReportListDto> returnGetBreakdownReportListDto = jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetBreakdownReportListDto.class));
            return Optional.of(returnGetBreakdownReportListDto);
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public void updateReportStatus(PostBreakdownReportRepairDto postBreakdownReportRepairDto) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bike_id", postBreakdownReportRepairDto.getBike_id());
        String sql = "UPDATE report SET status = 'resolved' WHERE status = 'received' AND bike_id =:bike_id";

        jdbcTemplate.update(sql, namedParameters);
    }
}
