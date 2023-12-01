package db.project.service;

import db.project.config.jwt.TokenProvider;
import db.project.domain.RefreshToken;
import db.project.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
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

    public void deleteById(String id) {
        refreshTokenRepository.deleteById(id);
    }
    @Transactional
    public String createNewAccessToken(String refreshToken, HttpServletRequest request) {
        //refreshToken 값 유무 확인
        if(refreshToken == null) {
            throw new IllegalArgumentException("refreshToken 값 없음");
        }
        //refreshToken 유효 기간 확인
        if(!tokenProvider.validToken(refreshToken, request)) {
            refreshTokenRepository.deleteByRefreshToken(refreshToken);
            throw new AuthenticationCredentialsNotFoundException("재로그인 바람");
        } else {
            RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new IllegalArgumentException("리프레시 토큰 없음"));
            String id = token.getId();
            return tokenProvider.createToken(id, 30 * 60 * 1000L);
        }
    }

}
