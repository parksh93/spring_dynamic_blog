package com.spring.blog.controller;

import com.spring.blog.config.jwt.TokenProvider;
import com.spring.blog.dto.AccessTokenResponseDTO;
import com.spring.blog.entity.User;
import com.spring.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;

@Controller
public class UserController {
    UsersService usersService;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    TokenProvider tokenProvider;

    @Autowired
    public UserController(UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenProvider tokenProvider){
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @RequestMapping("/login")
    public String login(){
        return "user/login";
    }
    @RequestMapping("/signup")
    public String signup(){
        return "user/signup";
    }
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signup(User user){
        usersService.save(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    @ResponseBody //REST컨트롤러가 아닌 컨트롤러에서 REST 형식으로 리턴하게 하는 어노테이션
    public ResponseEntity<?> authenticate(User user){
        // 폼에서 입력한 로그인 아이디를 이용해 DB에 저장된 전체 정보 얻어오기
        User userInfo = usersService.getByCredentials(user.getLoginId());

        // id를 통해 비밀번호를 얻어오고 비밀번호는 암호화 구문끼리 비교해야 한다 (BCryptPasswordEncoder에 match(평문비번, 암호비번)으로 비교)
        if (bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword())){
            // 비번이 맞다면 토큰 발급하는 2시간 통안 유효한 억세스 토큰 생성
            String token = tokenProvider.generateToken(userInfo, Duration.ofHours(2));

            // json으로 리턴하기 위해선 클래스 요소를 리턴해야 한다.
            AccessTokenResponseDTO accessTokenResponseDTO = new AccessTokenResponseDTO(token);

            return ResponseEntity.ok(accessTokenResponseDTO);
        }else{
            return ResponseEntity.badRequest().body("login failed");
        }
    }
}
