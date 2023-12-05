package db.project.service;

import db.project.dto.*;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.TicketException;
import db.project.repository.TicketRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void purchase(PostTicketPurchaseDto postTicketPurchaseDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        int hour = postTicketPurchaseDto.getHour();

        Map<String, Object> overfeeAndCash = ticketRepository.getCashAndTicketIdByUserId(user_id);
        if((int) overfeeAndCash.get("ticket_id") != 0) {
            throw new TicketException("ALREADY HAVE TICKET", ErrorCode.TICKET_DUPLICATION);
        }

        int cash = (int) overfeeAndCash.get("cash");

        TicketInfo ticketInfo = ticketRepository.getTicketInfoByHour(hour);

        if (ticketInfo.getPrice() > cash) {
            throw new TicketException("NOT ENOUGH MONEY", ErrorCode.NOT_ENOUGH_MONEY);
        }

        ticketRepository.updatePurchaseUserInfo(user_id, ticketInfo);
    }

    public TicketListResponseDto ticketList(){
        List<ReturnGetTicketInfoDto> ticketInfoList = ticketRepository.ticketList();
        TicketListResponseDto response = new TicketListResponseDto();
        for (ReturnGetTicketInfoDto ticketInfo : ticketInfoList) {
            response.getTickets().add(ticketInfo);
        }

        return response;
    }

    @Transactional
    public void gift(PostTicketGiftDto postTicketGiftDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        int hour = postTicketGiftDto.getHour();
        String phone_number = postTicketGiftDto.getPhone_number();

        Optional<String> existingTicketId = ticketRepository.getTicketIdByPhoneNumber(phone_number);
        if(existingTicketId.isPresent()) {
            TicketInfo ticketInfo = ticketRepository.getTicketInfoByHour(hour);

            int cash = ticketRepository.getCashByUserId(user_id);

            if (ticketInfo.getPrice() > cash) {
                throw new TicketException("NOT ENOUGH MONEY", ErrorCode.NOT_ENOUGH_MONEY);
            }
            ticketRepository.updateGiftGiverInfo(user_id, ticketInfo.getPrice());

            ticketRepository.updateGiftReceiverInfo(phone_number, ticketInfo.getTicketId());
        } else{
            throw new TicketException("ALREADY HAVE TICKET", ErrorCode.TICKET_DUPLICATION);
        }
    }
}
