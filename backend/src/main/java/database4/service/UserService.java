package database4.service;

import database4.dto.PostUserLoginDto;
import database4.dto.PostUserJoinDto;
import database4.dto.ReturnPostUserLoginDto;
import database4.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ReturnPostUserLoginDto login(PostUserLoginDto postUserLoginDto){
        return userRepository.login(postUserLoginDto);
    }

    public void join(PostUserJoinDto postUserJoinDto){
        userRepository.join(postUserJoinDto);
    }

}
