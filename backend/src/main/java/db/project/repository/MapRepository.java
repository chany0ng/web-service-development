package db.project.repository;

import db.project.dto.PostMapLocationInfoDto;
import db.project.dto.ReturnGetMapLocationDto;
import db.project.dto.ReturnPostMapLocationInfoDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MapRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MapRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReturnGetMapLocationDto> locationList(){
        String sql = "SELECT COUNT(bike_id) AS bikeCount, l.latitude, l.longitude " +
                "FROM location l LEFT JOIN bike b ON l.location_id = b.location_id AND b.status IN ('available', 'rented', 'closed') " +
                "WHERE l.status = 'available' GROUP BY l.location_id";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetMapLocationDto.class));
    }

    public Optional<ReturnPostMapLocationInfoDto> locationInfo(PostMapLocationInfoDto postMapLocationInfoDto) {
        String sql = "SELECT distinct l.location_id, l.address, l.status location_status, GROUP_CONCAT(b.bike_id) bike_id, GROUP_CONCAT(b.status) bike_status " +
                "FROM location l LEFT JOIN bike b ON l.location_id = b.location_id AND b.status IN ('available', 'rented', 'closed') " +
                "WHERE l.latitude = :latitude AND l.longitude = :longitude " +
                "GROUP BY l.location_id, l.address, l.status";

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("latitude", postMapLocationInfoDto.getLatitude())
                .addValue("longitude", postMapLocationInfoDto.getLongitude());

        try{
            ReturnPostMapLocationInfoDto locationInfoDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnPostMapLocationInfoDto.class));
            return Optional.of(locationInfoDto);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Boolean> getIsFavorite(String location_id, String user_id) {
        String sql = "SELECT IF(location_id = :location_id and user_id =:user_id, 1, 0) AS favorite FROM favorites";

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("location_id", location_id)
                .addValue("user_id", user_id);

        try{
            Boolean isFavorite = jdbcTemplate.queryForObject(sql, namedParameters, Boolean.class);
            return Optional.of(isFavorite);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
