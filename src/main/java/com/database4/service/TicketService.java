package com.database4.service;

import com.database4.dto.BikeInfo;
import com.database4.dto.PostTicketPurchaseDto;
import com.database4.dto.ReturnGetTicketInfoDto;
import com.database4.dto.TicketListResponseDto;
import com.database4.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public int purchase(PostTicketPurchaseDto postTicketPurchaseDto){
        return ticketRepository.purchase(postTicketPurchaseDto);
    }

    public TicketListResponseDto ticketList(){
        List<ReturnGetTicketInfoDto> ticketInfoList = ticketRepository.ticketList();
        TicketListResponseDto response = new TicketListResponseDto();
        for (ReturnGetTicketInfoDto ticketInfo : ticketInfoList) {
            response.getTickets().add(ticketInfo);
        }
        return response;
    }
}
