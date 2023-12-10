package db.project.repository;

import db.project.dto.PostBoardAndNoticeCreateAndUpdateDto;
import db.project.dto.ReturnGetBoardAndNoticeInfoDto;
import db.project.dto.ReturnGetBoardAndNoticeListDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BoardRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getBoardCount() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT count(board_id) boardCount FROM board";

        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Optional<List<ReturnGetBoardAndNoticeListDto>> boardList(int page) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page);
        String sql = "SELECT title, created_at as date, views FROM board LIMIT :page, 10";

        try {
            List<ReturnGetBoardAndNoticeListDto> returnGetBoardListDto = jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetBoardAndNoticeListDto.class));
            return Optional.of(returnGetBoardListDto);
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public Optional<ReturnGetBoardAndNoticeInfoDto> boardInfo(int boardId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("boardId", boardId);
        String sql = "SELECT user_id, title, content, created_at as date, views FROM board LIMIT :boardId, 1";
        try{
            ReturnGetBoardAndNoticeInfoDto returnGetBoardAndNoticeInfoDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetBoardAndNoticeInfoDto.class));

            return Optional.of(returnGetBoardAndNoticeInfoDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public void boardCreate(PostBoardAndNoticeCreateAndUpdateDto postBoardCreateDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", postBoardCreateDto.getTitle())
                .addValue("content", postBoardCreateDto.getContent())
                .addValue("user_id", user_id);
        String sql = "INSERT INTO board(user_id, title, content) values(:user_id, :title, :content)";
        jdbcTemplate.update(sql, namedParameters);
    }

    public Optional<Integer> getBoardId(int board_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("board_id", board_id);
        String sql = "SELECT board_id AS boardId FROM board limit :board_id, 1";
        try{
            int boardId = jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
            return Optional.of(boardId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public void boardUpdate(PostBoardAndNoticeCreateAndUpdateDto postBoardUpdateDto, int boardId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", postBoardUpdateDto.getTitle())
                .addValue("content", postBoardUpdateDto.getContent())
                .addValue("boardId", boardId);
        String sql = "UPDATE board SET title =:title, content =:content WHERE board_id =:boardId";

        int rowsUpdated = jdbcTemplate.update(sql, namedParameters);
    }

    public void boardDelete(int boardId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("boardId", boardId);
        String sql = "DELETE FROM board WHERE board_id =:boardId";

        int rowsUpdated = jdbcTemplate.update(sql, namedParameters);
    }

    public String isAuthor(int boardId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("boardId", boardId);
        String sql = "SELECT user_id FROM board WHERE board_id =:boardId";

        return jdbcTemplate.queryForObject(sql, namedParameters, String.class);
    }
}
