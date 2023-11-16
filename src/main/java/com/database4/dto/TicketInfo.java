package com.database4.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketInfo {
    private int ticketId;
    private int price;

    public TicketInfo() {
    }

    public TicketInfo(int ticketId, int price) {
        this.ticketId = ticketId;
        this.price = price;
    }
}
