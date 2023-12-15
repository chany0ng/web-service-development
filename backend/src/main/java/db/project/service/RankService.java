package db.project.service;

import db.project.dto.*;
import db.project.repository.RentalRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RankService {
    private final RentalRepository rentalRepository;

    public RankService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Transactional
    public RankDto.RankTimeResponse timeRank() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<RankDto.RankTime> allUserTimeRank = rentalRepository.findAllUserTimeRank();
        Optional<RankDto.RankTime> userTimeRank = rentalRepository.findUserTimeRank(user_id);

        if(userTimeRank.isPresent()) {
            RankDto.RankTimeResponse response = new RankDto.RankTimeResponse(userTimeRank.get().getRanking(), userTimeRank.get().getUser_id(), userTimeRank.get().getDuration_time());
            for(RankDto.RankTime rankTimeDto : allUserTimeRank) {
                response.getRank().add(rankTimeDto);
            }

            return response;
        } else{
            RankDto.RankTimeResponse response = new RankDto.RankTimeResponse();
            for(RankDto.RankTime rankTimeDto : allUserTimeRank) {
                response.getRank().add(rankTimeDto);
            }

            return response;
        }
    }

    @Transactional
    public RankDto.RankCountResponse countRank() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<RankDto.RankCount> allUsingCountRank = rentalRepository.findAllUserCountRank();
        Optional<RankDto.RankCount> userUsingCountRank = rentalRepository.findUserCountRank(user_id);

        if(userUsingCountRank.isPresent()) {
            RankDto.RankCountResponse response = new RankDto.RankCountResponse(userUsingCountRank.get().getRanking(), userUsingCountRank.get().getUser_id(), userUsingCountRank.get().getUsing_count());
            for(RankDto.RankCount rankCountDto : allUsingCountRank) {
                response.getRank().add(rankCountDto);
            }

            return response;
        } else{
            RankDto.RankCountResponse response = new RankDto.RankCountResponse();
            for(RankDto.RankCount rankCountDto : allUsingCountRank) {
                response.getRank().add(rankCountDto);
            }

            return response;
        }
    }
}
