package com.database4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGetMapLocationInfoDto {
    private String location_id;
    private String address;
    private String status;
    private int bikeCount;
    private boolean favorite;

    public ReturnGetMapLocationInfoDto() {
    }

    public ReturnGetMapLocationInfoDto(String location_id, String address, String status, int bikeCount, boolean favorite) {
        this.location_id = location_id;
        this.address = address;
        this.status = status;
        this.bikeCount = bikeCount;
        this.favorite = favorite;
    }
}
