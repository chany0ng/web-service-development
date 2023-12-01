package db.project.config.oauth;

import db.project.domain.User;
import db.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        saveOrLogin(user);
        return user;
    }
    @Transactional
    private void saveOrLogin(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String id = email.substring(0, email.indexOf("@")) + "_google";
        User user = userRepository.findUserById(id)
                .orElse(userRepository.save(User.builder()
                        .id(id)
                        .password("")
                        .phone_number("")
                        .pw_question(1)
                        .pw_answer("")
                        .email(email)
                        .role("user")
                        .build()).get()
                        );
    }

}
