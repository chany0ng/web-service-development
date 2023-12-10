package db.project.repository;

import db.project.dto.*;
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

    public int getNoticeCount() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT count(notice_id) noticeCount FROM Notice";

        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Optional<List<ReturnGetBoardAndNoticeListDto>> noticeList(int page) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page);
        String sql = "SELECT title, created_at as date, views FROM notice LIMIT :page, 10";

        try {
            List<ReturnGetBoardAndNoticeListDto> returnGetNoticeListDtoList = jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetBoardAndNoticeListDto.class));
            return Optional.of(returnGetNoticeListDtoList);
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public Optional<ReturnGetBoardAndNoticeInfoDto> noticeInfo(int noticeId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("noticeId", noticeId);
        String sql = "SELECT admin_id AS user_id, title, content, created_at as date, views FROM notice LIMIT :noticeId, 1";
        try{
            ReturnGetBoardAndNoticeInfoDto returnGetNoticeInfoDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetBoardAndNoticeInfoDto.class));

            return Optional.of(returnGetNoticeInfoDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public void noticeCreate(PostBoardAndNoticeCreateAndUpdateDto NoticeCreateDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", NoticeCreateDto.getTitle())
                .addValue("content", NoticeCreateDto.getContent())
                .addValue("user_id", user_id);
        String sql = "INSERT INTO notice(admin_id, title, content) values(:user_id, :title, :content)";
        jdbcTemplate.update(sql, namedParameters);
    }

    public Optional<Integer> getNoticeId(int notice_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("notice_id", notice_id);
        String sql = "SELECT notice_id AS noticeId FROM notice limit :notice_id, 1";
        try{
            int noticeId = jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
            return Optional.of(noticeId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public String isAuthor(int noticeId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("noticeId", noticeId);
        String sql = "SELECT admin_id AS user_id FROM notice WHERE notice_id =:noticeId";

        return jdbcTemplate.queryForObject(sql, namedParameters, String.class);
    }

    public void noticeUpdate(PostBoardAndNoticeCreateAndUpdateDto postNoticeUpdateDto, int noticeId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", postNoticeUpdateDto.getTitle())
                .addValue("content", postNoticeUpdateDto.getContent())
                .addValue("noticeId", noticeId);
        String sql = "UPDATE notice SET title =:title, content =:content WHERE notice_id =:noticeId";

        int rowsUpdated = jdbcTemplate.update(sql, namedParameters);
    }

    public void noticeDelete(int noticeId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("noticeId", noticeId);
        String sql = "DELETE FROM notice WHERE notice_id =:noticeId";

        int rowsUpdated = jdbcTemplate.update(sql, namedParameters);
    }
}
