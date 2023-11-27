package database4.repository;

import database4.dto.PostMapLocationInfoDto;
import database4.dto.ReturnGetMapLocationDto;
import database4.dto.ReturnPostMapLocationInfoDto;
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
        String sql = "SELECT COUNT(bike_id) AS bikeCount, l.latitude, l.longitude FROM location l LEFT JOIN BIKE b ON l.location_id = b.location_id " +
                "GROUP BY l.location_id";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        return jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetMapLocationDto.class));
    }

    public Optional<ReturnPostMapLocationInfoDto> locationInfo(PostMapLocationInfoDto postMapLocationInfoDto, String user_id) {
        String sql = "SELECT distinct l.location_id, l.address, l.status location_status, GROUP_CONCAT(b.bike_id) bike_id, GROUP_CONCAT(b.status) bike_status, " +
                "MAX(IF(f.location_id IS NULL, 0, 1)) AS favorite " +
                "FROM location l LEFT JOIN BIKE b ON l.location_id = b.location_id " +
                "LEFT JOIN favorites f ON f.location_id = l.location_id AND f.user_id = :user_id " +
                "WHERE l.latitude = :latitude AND l.longitude = :longitude " +
                "GROUP BY l.location_id, l.address, l.status";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("latitude", postMapLocationInfoDto.getLatitude())
                .addValue("longitude", postMapLocationInfoDto.getLongitude())
                .addValue("user_id", user_id);

        try{
            ReturnPostMapLocationInfoDto locationInfoDto = jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnPostMapLocationInfoDto.class));
            return Optional.of(locationInfoDto);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
