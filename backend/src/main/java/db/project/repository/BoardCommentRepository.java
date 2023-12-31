package db.project.repository;

import db.project.dto.BoardCommentDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public class BoardCommentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BoardCommentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BoardCommentDto.BoardComment> findCommentById(int board_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("board_id", board_id);

        String sql = "SELECT comment_id, content, created_at AS date, user_id FROM board_comments WHERE board_id =:board_id";

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(BoardCommentDto.BoardComment.class));
    }

    public Optional<String> findUserIdById(int comment_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("comment_id", comment_id);
        String sql = "SELECT user_id FROM board_comments WHERE comment_id =:comment_id";

        try {
            String user_id = jdbcTemplate.queryForObject(sql, namedParameters, String.class);
            return Optional.of(user_id);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void createComment(BoardCommentDto.BoardCommentCreateAndUpdate boardCommentCreateDto, String user_id, int board_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("content", boardCommentCreateDto.getContent())
                .addValue("user_id", user_id)
                .addValue("board_id", board_id);

        String sql = "INSERT INTO board_comments(user_id, board_id, content) values(:user_id, :board_id, :content)";

        jdbcTemplate.update(sql, namedParameters);
    }

    public void deleteCommentById(int comment_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("comment_id", comment_id);
        String sql = "DELETE FROM board_comments WHERE comment_id =:comment_id";

        int rowsUpdated = jdbcTemplate.update(sql, namedParameters);
    }

    public void updateCommentById(BoardCommentDto.BoardCommentCreateAndUpdate boardCommentUpdateDto, int comment_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("content", boardCommentUpdateDto.getContent())
                .addValue("comment_id", comment_id);

        String sql = "UPDATE board_comments SET content =:content WHERE comment_id =:comment_id";

        jdbcTemplate.update(sql, namedParameters);
    }
}
