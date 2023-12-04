package dp.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketListResponseDto {
    private List<ReturnGetTicketInfoDto> tickets;

    public TicketListResponseDto() {
        this.tickets = new ArrayList<>();
    }

    public TicketListResponseDto(List<ReturnGetTicketInfoDto> tickets) {
        this.tickets = tickets;
    }
}
