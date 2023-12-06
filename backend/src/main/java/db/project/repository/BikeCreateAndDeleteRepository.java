package db.project.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BikeCreateAndDeleteRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public void bikeCrete() {

    }

    public void bikeDelete() {

    }
}
