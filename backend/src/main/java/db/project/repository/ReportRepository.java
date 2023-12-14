package db.project.repository;

import db.project.dto.BreakdownReportDto;
import db.project.dto.ReturnGetAdminMainDto;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReportRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReportRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int findReportCountByStatus() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT count(report_id) reportCount FROM report WHERE status = 'received'";

        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Optional<List<BreakdownReportDto.BreakdownReportList>> findReportByStatus(int page) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page);
        String sql = "SELECT user_id, tire, chain, saddle, pedal, terminal, created_at, bike_id, status FROM report " +
                "WHERE status = 'received' ORDER BY report_id LIMIT :page, 10";

        try {
            List<BreakdownReportDto.BreakdownReportList> breakdownReportListDto = jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(BreakdownReportDto.BreakdownReportList.class));
            return Optional.of(breakdownReportListDto);
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public Optional<String> createReport(BreakdownReportDto.Report breakdownReportDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("bike_id", breakdownReportDto.getBike_id())
                .addValue("tire", breakdownReportDto.getTire())
                .addValue("chain", breakdownReportDto.getChain())
                .addValue("saddle", breakdownReportDto.getSaddle())
                .addValue("pedal", breakdownReportDto.getPedal())
                .addValue("terminal", breakdownReportDto.getTerminal());

        String insertReportSql = "INSERT INTO report(user_id, bike_id, tire, chain, saddle, pedal, terminal) " +
                "values(:user_id, :bike_id, :tire, :chain, :saddle, :pedal, :terminal)";
        int rowsUpdated = jdbcTemplate.update(insertReportSql, namedParameters);

        return (rowsUpdated > 0) ? Optional.of("고장신고가 접수되었습니다.") : Optional.empty();
    }

    public void updateStatusByStatusAndBike(BreakdownReportDto.BreakdownReportRepair breakdownReportRepairDto) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("bike_id", breakdownReportRepairDto.getBike_id());
        String sql = "UPDATE report SET status = 'resolved' WHERE status = 'received' AND bike_id =:bike_id";

        jdbcTemplate.update(sql, namedParameters);
    }

    public ReturnGetAdminMainDto adminMain(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String sql = "SELECT :user_id AS user_id, COUNT(report_id) AS report FROM report WHERE status = 'received'";

        ReturnGetAdminMainDto returnGetAdminMainDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetAdminMainDto.class));
        return returnGetAdminMainDto;
    }
}
