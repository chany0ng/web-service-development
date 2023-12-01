package db.project.controller;

import db.project.config.jwt.TokenProvider;
import db.project.domain.CreateTokenRequest;
import db.project.domain.CreateTokenResponse;
import db.project.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class TokenController {
    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;

    @PostMapping("/token")
    public ResponseEntity<CreateTokenResponse> createAccessToken(HttpServletRequest request, @RequestBody CreateTokenRequest createTokenRequest) {
        String accessToken = refreshTokenService.createNewAccessToken(createTokenRequest.getRefreshToken(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CreateTokenResponse.builder()
                        .accessToken(accessToken)
                        .build());
    }
}
