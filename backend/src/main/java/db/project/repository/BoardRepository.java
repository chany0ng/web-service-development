package db.project.repository;

import db.project.dto.PostBoardCreateAndUpdateDto;
import db.project.dto.ReturnGetBoardInfoDto;
import db.project.dto.ReturnGetBoardListDto;
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

    public Optional<List<ReturnGetBoardListDto>> boardList(int page) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page);
        String sql = "SELECT title, created_at as date FROM board LIMIT :page, 10";

        try {
            List<ReturnGetBoardListDto> returnGetBoardListDtoList = jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetBoardListDto.class));
            return Optional.of(returnGetBoardListDtoList);
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public Optional<ReturnGetBoardInfoDto> boardInfo(int boardId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("boardId", boardId);
        String sql = "SELECT user_id, title, content, created_at as date, views FROM board LIMIT :boardId, 1";
        try{
            ReturnGetBoardInfoDto returnGetBoardInfoDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetBoardInfoDto.class));

            return Optional.of(returnGetBoardInfoDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public void boardCreate(PostBoardCreateAndUpdateDto postBoardCreateAndUpdateDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", postBoardCreateAndUpdateDto.getTitle())
                .addValue("content", postBoardCreateAndUpdateDto.getContent())
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

    public void boardUpdate(PostBoardCreateAndUpdateDto postBoardCreateAndUpdateDto, int boardId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", postBoardCreateAndUpdateDto.getTitle())
                .addValue("content", postBoardCreateAndUpdateDto.getContent())
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
