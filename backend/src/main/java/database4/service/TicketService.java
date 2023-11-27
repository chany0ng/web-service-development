package database4.service;

import database4.dto.*;
import database4.exceptions.TicketPurchaseException;
import database4.repository.TicketRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public String purchase(PostTicketPurchaseDto postTicketPurchaseDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        int prevCash = postTicketPurchaseDto.getCash();
        int hour = postTicketPurchaseDto.getHour();

        Optional<String> existingTicketId = ticketRepository.getTicketIdByUserId(user_id);
        if(existingTicketId.isPresent()) {
            TicketInfo ticketInfo = ticketRepository.getTicketInfoByHour(hour).orElseThrow(() -> new TicketPurchaseException("잘못된 시간 정보가 전달되었습니다."));

            if (ticketInfo.getPrice() > prevCash) {
                throw new TicketPurchaseException("소지금이 부족합니다.");
            }

            boolean purchaseSuccess = ticketRepository.updateUserInfo(user_id, ticketInfo);

            return "이용권 구매에 성공했습니다.";
        } else{
            throw new TicketPurchaseException("이미 이용권을 보유 중입니다.");
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
