package db.project.controller;

import db.project.config.jwt.TokenProvider;
import db.project.dto.CreateTokenRequest;
import db.project.dto.CreateTokenResponse;
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

    @PostMapping("/auth/token")
    public ResponseEntity<CreateTokenResponse> createAccessToken(HttpServletRequest request, @RequestBody CreateTokenRequest createTokenRequest) {
        String accessToken = refreshTokenService.createNewAccessToken(createTokenRequest.getRefreshToken(), request);
        return ResponseEntity.ok(CreateTokenResponse.builder()
                .accessToken(accessToken)
                .build());
    }
}
