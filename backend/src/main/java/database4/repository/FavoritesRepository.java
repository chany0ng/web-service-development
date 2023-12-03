package database4.repository;

import database4.dto.PostFavoritesSearchDto;
import database4.dto.ReturnFavoritesDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FavoritesRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FavoritesRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReturnFavoritesDto> locationList(PostFavoritesSearchDto postFavoritesSearchDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("location", "%" + postFavoritesSearchDto.getLocation() + "%");
        String sql = "SELECT address, IF(f.location_id IS NULL, 0, 1) AS favorite FROM location l LEFT JOIN favorites f ON " +
                "l.location_id = f.location_id AND f.user_id =:user_id WHERE l.address LIKE :location ORDER BY favorite desc, address";
        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnFavoritesDto.class));
    }

    public Optional<String> locationAdd(String address, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("location", address);
        String sql = "INSERT INTO favorites(user_id, location_id) (SELECT :user_id, location_id FROM location WHERE address =:location)";
        try{
            jdbcTemplate.update(sql, namedParameters);
            return Optional.of("즐겨찾기 추가 성공");
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
    }

    public String locationDelete(String address, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("location", address);
        String sql = "DELETE FROM favorites WHERE user_id =:user_id AND " +
                "location_id = (SELECT location_id FROM location WHERE address =:location)";
        jdbcTemplate.update(sql, namedParameters);
        return "즐겨찾기 삭제 성공";
    }

    public List<ReturnFavoritesDto> locationList(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String sql = "SELECT address, IF(f.location_id IS NULL, 0, 1) AS favorite FROM location l JOIN favorites f ON " +
                "l.location_id = f.location_id WHERE f.user_id =:user_id ORDER BY favorite desc, address";
        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnFavoritesDto.class));
    }
}
