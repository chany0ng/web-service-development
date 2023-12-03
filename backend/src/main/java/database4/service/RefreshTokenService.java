package database4.service;

import database4.config.jwt.TokenProvider;
import database4.domain.RefreshToken;
import database4.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    public String save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken.getId(), refreshToken.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("중복된 아이디입니다."));
    }
    @Transactional
    public String createNewAccessToken(String refreshToken, HttpServletRequest request) {
        if(!tokenProvider.validToken(refreshToken, request)) {
            throw new IllegalArgumentException("재발급 오류");
        }
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("리프레시 토큰 없음"));

        String id = token.getId();
        return tokenProvider.createToken(id, 30 * 60 * 1000L);
    }

}