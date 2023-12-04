package dp.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGetTicketInfoDto {
    private int hour;
    private int price;

    public ReturnGetTicketInfoDto() {
    }

    public ReturnGetTicketInfoDto(int hour, int price) {
        this.hour = hour;
        this.price = price;
    }
}
