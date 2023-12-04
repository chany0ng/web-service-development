package db.project.repository;

import db.project.dto.PostChargeDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChargeRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ChargeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean charge(PostChargeDto postChargeDto, String user_id){
        String sql = "UPDATE user SET cash = cash + :cash Where user_id = :user_id";
        final MapSqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", user_id)
                .addValue("cash", postChargeDto.getCash());
        int checkChargeUpdate = jdbcTemplate.update(sql, namedParameters);
        if(checkChargeUpdate > 0){
            return true;
        } else{
            return false;
        }
    }
}