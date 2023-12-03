package database4.service;

import database4.domain.User;
import database4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("등록된 사용자가 아닙니다."));
    }

}