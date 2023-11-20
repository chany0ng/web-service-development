package db.project.service;

import db.project.config.jwt.TokenProvider;
import db.project.domain.Token;
import db.project.domain.User;
import db.project.domain.UserLoginRequest;
import db.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public String save(User user) {
        return userRepository.save(user)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + user.getId()));
    }

    @Transactional
    public Token login(UserLoginRequest userLoginRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findUserById(userLoginRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("not found: " + userLoginRequest.getUserId()));
        if(!bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        String token = tokenProvider.createToken(user.getId());
        return Token.builder()
                .token(token)
                .build();
    }

}
