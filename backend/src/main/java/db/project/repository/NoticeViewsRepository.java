package db.project.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class NoticeViewsRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public NoticeViewsRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Integer> findIdByNoticeAndUser(int noticeId, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("noticeId", noticeId)
                .addValue("user_id", user_id);

        String sql = "SELECT view_id FROM notice_views WHERE notice_id =:noticeId AND admin_id =:user_id";

        try {
            int view_id = jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
            return Optional.of(view_id);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Integer> createNoticeViews(int noticeId, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("noticeId", noticeId)
                .addValue("user_id", user_id);

        String sql = "INSERT INTO notice_views(notice_id, admin_id) VALUES(:noticeId, :user_id)";

        try{
            int check = jdbcTemplate.update(sql, namedParameters);
            return Optional.of(check);
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }

    }
}
