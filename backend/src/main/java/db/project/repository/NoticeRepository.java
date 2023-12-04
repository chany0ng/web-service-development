package db.project.repository;

import db.project.dto.ReturnGetNoticeInfoDto;
import db.project.dto.ReturnGetNoticeListDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NoticeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public NoticeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<ReturnGetNoticeListDto>> noticeList(int page) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page);
        String sql = "SELECT title, created_at as date FROM notice LIMIT :page, 10";

        try {
            List<ReturnGetNoticeListDto> returnGetNoticeListDtoList = jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetNoticeListDto.class));
            return Optional.of(returnGetNoticeListDtoList);
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public Optional<ReturnGetNoticeInfoDto> noticeInfo(int noticeId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("noticeId", noticeId);
        String sql = "SELECT title, content, created_at as date, views FROM notice LIMIT :noticeId, 1";
        try{
            ReturnGetNoticeInfoDto returnGetNoticeInfoDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetNoticeInfoDto.class));

            return Optional.of(returnGetNoticeInfoDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }
}
