package db.project.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BoardViewsRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BoardViewsRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Integer> getUserIdAndBoardId(int boardId, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("boardId", boardId)
                .addValue("user_id", user_id);

        String sql = "SELECT view_id FROM board_views WHERE board_id =:boardId AND user_id = user_id";

        try {
            int view_id = jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
            return Optional.of(view_id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Integer> insertBoardViews(int boardId, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("boardId", boardId)
                .addValue("user_id", user_id);

        String sql = "INSERT INTO board_views(board_id, user_id) VALUES(:boardId, :user_id)";

        try{
            int check = jdbcTemplate.update(sql, namedParameters);
            return Optional.of(check);
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }
    }
}
