package database4.controller;

import database4.config.jwt.TokenProvider;
import database4.domain.CreateTokenRequest;
import database4.domain.CreateTokenResponse;
import database4.service.RefreshTokenService;
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
