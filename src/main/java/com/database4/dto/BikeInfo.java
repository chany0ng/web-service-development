package com.database4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BikeInfo {  // 자전거와 관련된 정보를 구조화
    private String bike_id;
    private String bike_status;

    public BikeInfo() {
    }

    public BikeInfo(String bike_id, String bike_status) {
        this.bike_id = bike_id;
        this.bike_status = bike_status;
    }
}
