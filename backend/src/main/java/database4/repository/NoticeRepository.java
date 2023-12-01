package database4.repository;

import database4.dto.ReturnGetBoardListDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public NoticeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    public void noticeList(int page) {
//        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
//                .addValue("page", page);
//        String sql = "SELECT title, created_at as date FROM board LIMIT :page, 10";
//
//        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetBoardListDto.class));
//    }

    public void noticeInfo() {

    }
}
