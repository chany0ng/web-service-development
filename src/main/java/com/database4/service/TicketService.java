package com.database4.service;

import com.database4.dto.PostTicketPurchaseDto;
import com.database4.dto.ReturnGetTicketPurchaseDto;
import com.database4.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public int purchase(PostTicketPurchaseDto postTicketPurchaseDto){
        return ticketRepository.purchase(postTicketPurchaseDto);
    }

    public List<String> ticketList(){
        List<ReturnGetTicketPurchaseDto> returnGetTicketPurchaseDtoList  = ticketRepository.ticketList();
        return returnGetTicketPurchaseDtoList.stream()
                .map(ReturnGetTicketPurchaseDto::getHour)
                .collect(Collectors.toList());
    }
}
