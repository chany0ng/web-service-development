package database4.service;

import database4.config.jwt.TokenProvider;
import database4.domain.RefreshToken;
import database4.domain.UserLoginResponse;
import database4.domain.User;
import database4.domain.UserLoginRequest;
import database4.repository.UserRepository;
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

    public void save(User user) {
        userRepository.save(user)
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
        String accessToken = tokenProvider.createToken(user.getId(),  accessTokenValidTime);
        String refreshToken = tokenProvider.createToken(user.getId(), refreshTokenValidTime);

        refreshTokenService.save(RefreshToken.builder()
                .id(user.getId())
                .refreshToken(refreshToken)
                .build());

        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole())
                .build();
    }

    public void logout() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        refreshTokenService.deleteById(id);
    }

}
