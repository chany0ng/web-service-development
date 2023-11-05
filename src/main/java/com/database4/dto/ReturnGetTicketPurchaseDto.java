package com.database4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGetTicketPurchaseDto {
    private String hour;

    public ReturnGetTicketPurchaseDto() {
    }

    public ReturnGetTicketPurchaseDto(String hour) {
        this.hour = hour;
    }
}
