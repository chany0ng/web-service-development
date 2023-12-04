package db.project.config.oauth;

import db.project.config.jwt.TokenProvider;
import db.project.dto.User;
import db.project.repository.RefreshTokenRepository;
import db.project.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    private long accessTokenValidTime = 60 * 60 * 1000L; //1시간
    private long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L; //일주일

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        String id = email.substring(0, email.indexOf("@")) + "_google";
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 사용자"));

        String accessToken = tokenProvider.createToken(id, accessTokenValidTime);
        String refreshToken = tokenProvider.createToken(id, refreshTokenValidTime);

        refreshTokenRepository.save(id, refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("중복된 아이디"));

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{ \"accessToken\": \"" + accessToken + "\", \"refreshToken\": \""+ refreshToken + "\" } ");

        getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/user/main");
    }
}
