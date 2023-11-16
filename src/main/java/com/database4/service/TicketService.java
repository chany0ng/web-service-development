package com.database4.service;

import com.database4.dto.BikeInfo;
import com.database4.dto.PostTicketPurchaseDto;
import com.database4.dto.ReturnGetTicketInfoDto;
import com.database4.dto.TicketListResponseDto;
import com.database4.exceptions.TicketPurchaseException;
import com.database4.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public String purchase(PostTicketPurchaseDto postTicketPurchaseDto){
        try {
            return ticketRepository.purchase(postTicketPurchaseDto)
                    .orElseThrow(() -> new TicketPurchaseException("티켓 구매에 실패했습니다."));
        } catch (TicketPurchaseException e) {
            throw e;
        }
    }

    public TicketListResponseDto  ticketList(){
        List<ReturnGetTicketInfoDto> ticketInfoList = ticketRepository.ticketList();
        TicketListResponseDto response = new TicketListResponseDto();
        for (ReturnGetTicketInfoDto ticketInfo : ticketInfoList) {
            response.getTickets().add(ticketInfo);
        }

        return response;
    }
}
