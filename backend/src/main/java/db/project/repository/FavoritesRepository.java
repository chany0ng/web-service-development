package db.project.repository;

import db.project.dto.FavoritesDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public List<FavoritesDto.Favorites> findFavoritesAndLocation(FavoritesDto.FavoritesSearch favoritesSearchDto, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("location", "%" + favoritesSearchDto.getLocation() + "%");
        String sql = "SELECT l.location_id, address, IF(f.location_id IS NULL, 0, 1) AS favorite FROM location l LEFT JOIN favorites f ON " +
                "l.location_id = f.location_id AND f.user_id =:user_id WHERE l.address LIKE :location ORDER BY favorite desc, l.location_id";
        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(FavoritesDto.Favorites.class));
    }

    public List<FavoritesDto.Favorites> findFavoritesByUserId(String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id);
        String sql = "SELECT f.location_id, address, IF(f.location_id IS NULL, 0, 1) AS favorite FROM favorites f JOIN location l ON " +
                "l.location_id = f.location_id WHERE f.user_id =:user_id ORDER BY favorite desc, l.location_id";
        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(FavoritesDto.Favorites.class));
    }

    public Optional<Boolean> findFavoriteById(String location_id, String user_id) {
        String sql = "SELECT IF(location_id = :location_id and user_id =:user_id, 1, 0) AS favorite FROM favorites where location_id = :location_id and user_id =:user_id";

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("location_id", location_id)
                .addValue("user_id", user_id);

        try{
            Boolean isFavorite = jdbcTemplate.queryForObject(sql, namedParameters, Boolean.class);
            return Optional.of(isFavorite);
        } catch(EmptyResultDataAccessException e) {
            return Optional.of(false);
        }
    }

    public Optional<String> createFavorites(String address, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("address", address);
        String sql = "INSERT INTO favorites(user_id, location_id) (SELECT :user_id, location_id FROM location WHERE address =:address)";
        try{
            jdbcTemplate.update(sql, namedParameters);
            return Optional.of("즐겨찾기 추가 성공");
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
    }

    public String deleteFavoritesById(String address, String user_id) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("location", address);
        String sql = "DELETE FROM favorites WHERE user_id =:user_id AND " +
                "location_id = (SELECT location_id FROM location WHERE address =:location)";
        jdbcTemplate.update(sql, namedParameters);
        return "즐겨찾기 삭제 성공";
    }
}
