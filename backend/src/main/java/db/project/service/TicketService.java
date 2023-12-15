package db.project.service;

import db.project.dto.*;
import db.project.exceptions.ErrorCode;
import db.project.exceptions.TicketException;
import db.project.repository.TicketRepository;
import db.project.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void purchase(PostTicketPurchaseDto postTicketPurchaseDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        int hour = postTicketPurchaseDto.getHour();

        Map<String, Object> overfeeAndCash = userRepository.findCashAndTicketById(user_id);
        if((int) overfeeAndCash.get("ticket_id") != 0) {
            throw new TicketException("ALREADY HAVE TICKET", ErrorCode.TICKET_DUPLICATION);
        }

        int cash = (int) overfeeAndCash.get("cash");

        TicketInfo ticketInfo = ticketRepository.findIdAndPriceByHour(hour);

        if (ticketInfo.getPrice() > cash) {
            throw new TicketException("NOT ENOUGH MONEY", ErrorCode.NOT_ENOUGH_MONEY);
        }

        userRepository.updateCashAndTicketById(user_id, ticketInfo);
    }

    public TicketListResponseDto ticketList(){
        List<ReturnGetTicketInfoDto> ticketInfoList = ticketRepository.findHourAndPrice();
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
        String receivedUser_id = postTicketGiftDto.getUser_id();

        Optional<String> existingTicketId = userRepository.findTicketById(receivedUser_id);
        if(existingTicketId.isPresent()) {
            TicketInfo ticketInfo = ticketRepository.findIdAndPriceByHour(hour);

            int cash = userRepository.findCashById(user_id);

            if (ticketInfo.getPrice() > cash) {
                throw new TicketException("NOT ENOUGH MONEY", ErrorCode.NOT_ENOUGH_MONEY);
            }

            userRepository.updateCashById(user_id, -ticketInfo.getPrice());

            userRepository.updateTicketById(receivedUser_id, ticketInfo.getTicketId());
        } else{
            throw new TicketException("ALREADY HAVE TICKET", ErrorCode.TICKET_DUPLICATION);
        }
    }
}
