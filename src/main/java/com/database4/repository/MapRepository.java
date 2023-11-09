package com.database4.repository;

import com.database4.dto.PostMapLocationInfoDto;
import com.database4.dto.ReturnGetMapLocationDto;
import com.database4.dto.ReturnPostMapLocationInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
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

    public ReturnPostMapLocationInfoDto locationInfo(PostMapLocationInfoDto postMapLocationInfoDto){
        log.info("latitude " + postMapLocationInfoDto.getLatitude());
        log.info("longitude " + postMapLocationInfoDto.getLongitude());
//        String sql = "SELECT l.location_id, l.address, l.status location_status, b.bike_id, b.status bike_status, " +
//                "IF(f.location_id IS NULL, 0, 1) AS favorite " +
//                "FROM location l LEFT JOIN BIKE b ON l.location_id = b.location_id " +
//                "LEFT JOIN favorites f ON f.location_id = l.location_id AND f.user_id = :user_id " +
//                "WHERE l.latitude = :latitude AND l.longitude = :longitude";

        String sql = "SELECT distinct l.location_id, l.address, l.status location_status, GROUP_CONCAT(b.bike_id) bike_id, GROUP_CONCAT(b.status) bike_status, " +
                "MAX(IF(f.location_id IS NULL, 0, 1)) AS favorite " +
                "FROM location l LEFT JOIN BIKE b ON l.location_id = b.location_id " +
                "LEFT JOIN favorites f ON f.location_id = l.location_id AND f.user_id = :user_id " +
                "WHERE l.latitude = :latitude AND l.longitude = :longitude " +
                "GROUP BY l.location_id, l.address, l.status";

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("latitude", postMapLocationInfoDto.getLatitude())
                .addValue("longitude", postMapLocationInfoDto.getLongitude())
                .addValue("user_id", postMapLocationInfoDto.getUser_id());

        return jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnPostMapLocationInfoDto.class));
    }
}
