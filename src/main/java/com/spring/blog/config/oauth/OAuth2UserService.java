package com.spring.blog.config.oauth;

import com.spring.blog.entity.User;
import com.spring.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

// 원래는 DefaultOAuth2UserSevice 클래스를 이용해 만들면 처리가 서비스단에서 작동
// 다만 이 경우 기본적으로 제공하는 필드나 기능에 대해서만 구현되어 있으므로 상속을 통해 기능을 확장시켜 사용
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    // 소셜 로그인 관련 서비스 레이이도 어차피 회원 기능 관련이므로 유저 레퍼지토리쪽에 관여합니다.
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 소셜 로그인 요청이 들어오면 해당 정보를 클라이언트 큭에서 사용하는 User 객체로 반환
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    public User saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attirbutes = oAuth2User.getAttributes();
        String email = (String) attirbutes.get("email"); //email 변수에 담겨있던 이메일 값 받아오기
        String name = (String) attirbutes.get("name");  // loginId 받아고기
        User user = userRepository.findByLoginId(name);

        if(user == null){
            user = User.builder().email(email).loginId(name).build();
        }else{
            user.update(name);
        }

        return userRepository.save(user);
    }
}
