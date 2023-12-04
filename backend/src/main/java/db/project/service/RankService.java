package db.project.service;

import db.project.dto.RankCountResponseDto;
import db.project.dto.RankTimeResponseDto;
import db.project.dto.ReturnGetRankCountDto;
import db.project.dto.ReturnGetRankTimeDto;
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
    public RankTimeResponseDto timeRank() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ReturnGetRankTimeDto> allUserTimeRank = rankRepository.allUserTimeRank();
        Optional<ReturnGetRankTimeDto> userTimeRank = rankRepository.userTimeRank(user_id);

        if(userTimeRank.isPresent()) {
            RankTimeResponseDto response = new RankTimeResponseDto(userTimeRank.get().getRanking(), userTimeRank.get().getUser_id(), userTimeRank.get().getDuration_time());
            for(ReturnGetRankTimeDto returnGetRankTimeDto : allUserTimeRank) {
                response.getRank().add(returnGetRankTimeDto);
            }

            return response;
        } else{
            RankTimeResponseDto response = new RankTimeResponseDto();
            for(ReturnGetRankTimeDto returnGetRankTimeDto : allUserTimeRank) {
                response.getRank().add(returnGetRankTimeDto);
            }

            return response;
        }
    }

    @Transactional
    public RankCountResponseDto countRank() {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ReturnGetRankCountDto> allUsingCountRank = rankRepository.allUsingCountRank();
        Optional<ReturnGetRankCountDto> userUsingCountRank = rankRepository.userUsingCountRank(user_id);

        if(userUsingCountRank.isPresent()) {
            RankCountResponseDto response = new RankCountResponseDto(userUsingCountRank.get().getRanking(), userUsingCountRank.get().getUser_id(), userUsingCountRank.get().getUsing_count());
            for(ReturnGetRankCountDto returnGetRankCountDto : allUsingCountRank) {
                response.getRank().add(returnGetRankCountDto);
            }

            return response;
        } else{
            RankCountResponseDto response = new RankCountResponseDto();
            for(ReturnGetRankCountDto returnGetRankCountDto : allUsingCountRank) {
                response.getRank().add(returnGetRankCountDto);
            }

            return response;
        }
    }
}
