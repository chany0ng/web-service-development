package com.database4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGetMapLocationDto {
    private int bikeCount;
    private float latitude;
    private float longitude;

    public ReturnGetMapLocationDto() {
    }

    public ReturnGetMapLocationDto(float latitude, float longitude, int bikeCount) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.bikeCount = bikeCount;
    }
}
