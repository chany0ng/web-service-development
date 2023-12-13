package db.project.repository;

import db.project.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public int getLocationCount() {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String sql = "SELECT COUNT(location_id) locationCount FROM location WHERE status = 'available'";

        return jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public Optional<List<ReturnGetLocationListDto>> locationList(int page) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("page", page);
        String sql = "SELECT l.location_id, address, COUNT(bike_id) AS bikeCount FROM location l LEFT JOIN bike b ON l.location_id = b.location_id " +
                "WHERE l.status = 'available' GROUP BY l.location_id ORDER BY l.location_id LIMIT :page, 10";

        try {
            List<ReturnGetLocationListDto> returnGetLocationListDto = jdbcTemplate.query(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetLocationListDto.class));
            return Optional.of(returnGetLocationListDto);
        } catch (BadSqlGrammarException e) {
            return Optional.empty();
        }
    }

    public Optional<String> locationCreate(PostLocationCreateDto postLocationCreateDto) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("location_id", postLocationCreateDto.getLocation_id())
                .addValue("address", postLocationCreateDto.getAddress())
                .addValue("latitude", postLocationCreateDto.getLatitude())
                .addValue("longitude", postLocationCreateDto.getLongitude());
        String sql = "INSERT INTO location(location_id, address, latitude, longitude) values(:location_id, :address, :latitude, :longitude)";

        try{
            jdbcTemplate.update(sql, namedParameters);
            return Optional.of("대여소 생성 성공");
        } catch (DuplicateKeyException e) {
            return Optional.empty();
        }
    }

    public int locationDelete(PostLocationDeleteDto postLocationDeleteDto) {
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("location_id", postLocationDeleteDto.getLocation_id());

        String sql = "UPDATE location SET status = 'deleted' WHERE location_id =:location_id AND status = 'available'";

        return jdbcTemplate.update(sql, namedParameters);
    }
}
