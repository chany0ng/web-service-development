package com.database4.service;

import com.database4.dto.PostUserLoginDto;
import com.database4.dto.PostUserJoinDto;
import com.database4.dto.ReturnPostUserLoginDto;
import com.database4.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

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
