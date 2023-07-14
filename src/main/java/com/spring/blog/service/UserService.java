package com.spring.blog.service;

import com.spring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor // 빈 컨테이너에 등록
public class UserService implements UserDetailsService {    // 인터페이스는 이미 라이브러리에 내장, 구현체만 만들면 된다.
    private  final UserRepository userRepository;

    @Autowired  // 스프링 4번전부터 단일 멤버변수는 생성자만 있으면 자동 주입된다.
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override   // 회원 관련해서는 로그인 되는지 여부만 따지므로 이것만 구현하면 된다.
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return userRepository.findByLoginId(loginId);   //회원정보 리턴
    }
}
