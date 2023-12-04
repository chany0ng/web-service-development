package db.project.controller;

import db.project.service.RefreshTokenService;
import db.project.config.jwt.TokenProvider;
import db.project.domain.CreateTokenRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class TokenController {
    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;

    @PostMapping("/token")
    public ResponseEntity<String> createAccessToken(HttpServletRequest request, @RequestBody CreateTokenRequest createTokenRequest) {
        //System.out.println(refreshToken);
        String accessToken = refreshTokenService.createNewAccessToken(createTokenRequest.getRefreshToken(), request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accessToken);
        //return ResponseEntity.ok("ok");
    }
}