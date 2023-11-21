package com.database4.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RentalHistoryResponseDto {

    List<ReturnPostRentalHistoryDto> RentalInfo;

    public RentalHistoryResponseDto() {
        this.RentalInfo = new ArrayList<>();
    }

    public RentalHistoryResponseDto(List<ReturnPostRentalHistoryDto> RentalInfo) {
        this.RentalInfo = RentalInfo;
    }
}
