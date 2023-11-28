package db.project.service;

import db.project.config.jwt.TokenProvider;
import db.project.domain.RefreshToken;
import db.project.domain.UserLoginResponse;
import db.project.domain.User;
import db.project.domain.UserLoginRequest;
import db.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    private long accessTokenValidTime = 60 * 60 * 1000L; //1시간
    private long refreshTokenValidTime = 7 * 24 * 60 * 60 * 1000L; //일주일

    public String save(User user) {
        return userRepository.save(user)
                .orElseThrow(() -> new IllegalArgumentException("중복된 아이디입니다."));
    }

    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        if(userLoginRequest.getId() == null) {
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }

        User user = userRepository.findUserById(userLoginRequest.getId())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디입니다."));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if(!bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        String accessToken = tokenProvider.createToken(user.getId(), 20 * 1000L);
        String refreshToken = tokenProvider.createToken(user.getId(), 5 * 60 * 1000L);

        refreshTokenService.save(RefreshToken.builder()
                .id(user.getId())
                .refreshToken(refreshToken)
                .build());

        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        refreshTokenService.deleteById(id);
    }

}
