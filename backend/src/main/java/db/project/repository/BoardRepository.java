package db.project.repository;

import db.project.dto.*;
import org.springframework.dao.DataIntegrityViolationException;
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

    public int findBoardCount() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT count(board_id) boardCount FROM board";

        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Optional<List<BoardDto.BoardList>> findBoard(int page) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page);
        String sql = "SELECT board_id, title, created_at as date, views FROM board ORDER BY board_id DESC LIMIT :page, 10";

        try {
            List<BoardDto.BoardList> boardListDto = jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(BoardDto.BoardList.class));
            return Optional.of(boardListDto);
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public Optional<BoardDto.BoardInfo> findBoardById(int board_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("board_id", board_id);
        String sql = "SELECT board_id, user_id, title, content, created_at as date, views FROM board WHERE board_id =:board_id";
        try{
            BoardDto.BoardInfo boardInfoDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(BoardDto.BoardInfo.class));

            return Optional.of(boardInfoDto);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<String> findUserIdById(int boardId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("boardId", boardId);
        String sql = "SELECT user_id FROM board WHERE board_id =:boardId";

        try {
            String user_id = jdbcTemplate.queryForObject(sql, namedParameters, String.class);
            return Optional.of(user_id);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void createBoard(BoardDto.BoardCreateAndUpdate boardCreateDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", boardCreateDto.getTitle())
                .addValue("content", boardCreateDto.getContent())
                .addValue("user_id", user_id);
        String sql = "INSERT INTO board(user_id, title, content) values(:user_id, :title, :content)";
        jdbcTemplate.update(sql, namedParameters);
    }

    public void updateBoardById(BoardDto.BoardCreateAndUpdate boardUpdateDto, int boardId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("title", boardUpdateDto.getTitle())
                .addValue("content", boardUpdateDto.getContent())
                .addValue("boardId", boardId);
        String sql = "UPDATE board SET title =:title, content =:content WHERE board_id =:boardId";

        int rowsUpdated = jdbcTemplate.update(sql, namedParameters);
    }

    public void updateViewsById(int boardId) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("boardId", boardId);

        String sql = "UPDATE board SET views = views + 1 WHERE board_id =:boardId";

        jdbcTemplate.update(sql, namedParameters);
    }

    public void deleteBoardById(BoardDto.BoardDelete boardDeleteDto) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("boardId", boardDeleteDto.getBoard_id());
        String sql = "DELETE FROM board WHERE board_id =:boardId";

        int rowsUpdated = jdbcTemplate.update(sql, namedParameters);
    }
}
