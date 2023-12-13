package db.project.service;

import db.project.dto.*;
import db.project.repository.RankRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RankService {
    private final RankRepository rankRepository;

    public RankService(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }

    @Transactional
    public RankDto.RankTimeResponse timeRank() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<RankDto.RankTime> allUserTimeRank = rankRepository.allUserTimeRank();
        Optional<RankDto.RankTime> userTimeRank = rankRepository.userTimeRank(user_id);

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

        List<RankDto.RankCount> allUsingCountRank = rankRepository.allUsingCountRank();
        Optional<RankDto.RankCount> userUsingCountRank = rankRepository.userUsingCountRank(user_id);

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
