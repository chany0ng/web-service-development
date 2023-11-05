package com.database4.repository;

import com.database4.dto.PostGetMapLocationInfoDto;
import com.database4.dto.ReturnGetMapLocationDto;
import com.database4.dto.ReturnGetMapLocationInfoDto;
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

    public ReturnGetMapLocationInfoDto locationInfo(PostGetMapLocationInfoDto postGetMapLocationInfoDto){
        log.info("latitude " + postGetMapLocationInfoDto.getLatitude());
        log.info("longitude " + postGetMapLocationInfoDto.getLongitude());
        String sql = "SELECT COUNT(b.bike_id) AS bikeCount, l.location_id, l.address, l.status, " +
                "IF(f.location_id IS NULL, 0, 1) AS favorite " +
                "FROM location l LEFT JOIN BIKE b ON l.location_id = b.location_id " +
                "LEFT JOIN favorites f ON f.location_id = l.location_id AND f.user_id = :user_id " +
                "WHERE l.latitude = :latitude AND l.longitude = :longitude GROUP BY l.location_id";

        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("latitude", postGetMapLocationInfoDto.getLatitude())
                .addValue("longitude", postGetMapLocationInfoDto.getLongitude())
                .addValue("user_id", postGetMapLocationInfoDto.getUser_id());

        return jdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<>(ReturnGetMapLocationInfoDto.class));
    }
}
