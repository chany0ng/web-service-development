package database4.service;

import database4.repository.RankRepository;
import org.springframework.stereotype.Service;

@Service
public class RankService {
    private final RankRepository rankRepository;

    public RankService(RankRepository rankRepository) {
        this.rankRepository = rankRepository;
    }
}
