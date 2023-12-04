package db.project.repository;

import db.project.dto.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public Optional<String> save(String id, String refreshToken) {
        try {
            String sql = "INSERT INTO refreshtoken (user_id, refreshtoken) VALUES (:id, :refreshToken)";
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource("id", id)
                    .addValue("refreshToken", refreshToken);
            namedParameterJdbcTemplate.update(sql, sqlParameterSource);

            return Optional.of(id);
        } catch (DuplicateKeyException e){
            return Optional.empty();
        }

    }

    public Optional<RefreshToken> findByRefreshToken(String token) {
        String sql = "SELECT * FROM refreshtoken WHERE REFRESHTOKEN= :refreshToken";
        try {
            SqlParameterSource sqlParameterSource = new MapSqlParameterSource("refreshToken", token);
            RefreshToken refreshToken = namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, refreshTokenMapper);
            return Optional.of(refreshToken);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    private final RowMapper<RefreshToken> refreshTokenMapper = (rs, rowNum) -> {
        RefreshToken refreshToken = RefreshToken.builder()
                .id(rs.getString("user_id"))
                .refreshToken("refreshtoken")
                .build();
        return refreshToken;
    };

}