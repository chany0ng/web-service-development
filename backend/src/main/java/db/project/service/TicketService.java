package db.project.service;

import database4.dto.*;
import db.project.dto.*;
import dp.project.dto.*;
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
    public String purchase(PostTicketPurchaseDto postTicketPurchaseDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        int hour = postTicketPurchaseDto.getHour();

        Map<String, Object> overfeeAndCash = ticketRepository.getCashAndTicketIdByUserId(user_id);
        if((int) overfeeAndCash.get("ticket_id") != 0) {
            throw new TicketException("이미 이용권을 보유 중입니다.");
        }

        int cash = (int) overfeeAndCash.get("cash");

        TicketInfo ticketInfo = ticketRepository.getTicketInfoByHour(hour).orElseThrow(() -> new TicketException("잘못된 시간 정보가 전달되었습니다."));

        if (ticketInfo.getPrice() > cash) {
            throw new TicketException("소지금이 부족합니다.");
        }

        ticketRepository.updatePurchaseUserInfo(user_id, ticketInfo);

        return "이용권 구매에 성공했습니다.";
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
    public String gift(PostTicketGiftDto postTicketGiftDto){
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        int hour = postTicketGiftDto.getHour();
        String phone_number = postTicketGiftDto.getPhone_number();

        Optional<String> existingTicketId = ticketRepository.getTicketIdByPhoneNumber(phone_number);
        if(existingTicketId.isPresent()) {
            TicketInfo ticketInfo = ticketRepository.getTicketInfoByHour(hour).orElseThrow(() -> new TicketException("잘못된 시간 정보가 전달되었습니다."));

            int cash = ticketRepository.getCashByUserId(user_id);

            if (ticketInfo.getPrice() > cash) {
                throw new TicketException("소지금이 부족합니다.");
            }
            ticketRepository.updateGiftGiverInfo(user_id, ticketInfo.getPrice());
            ticketRepository.updateGiftReceiverInfo(phone_number, ticketInfo.getTicketId());

            return "이용권 선물에 성공했습니다.";
        } else{
            throw new TicketException("해당 사용자가 이용권을 보유 중입니다.");
        }
    }
}
