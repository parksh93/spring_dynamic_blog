package com.spring.blog.config;

import com.spring.blog.service.UserService;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration  // 설정 클래스 상위에 붙이는 애노테이션
public class BasicSecurityConfig {  //베이직 방식 인증
    // 등록할 시큐리티 서비스
    private final UserDetailsService userService;
    @Autowired
    public BasicSecurityConfig(UserDetailsService userService){
        this.userService = userService;
    }


    // 정적 파일이나 .jsp 파일 등 스프링 시큐리티가 기본적으로 적용되지 않을 영역설정
    @Bean   //Configuration 어노테이션 붙은 클래스 내부 메서드가 리턴하는 자료는 자동으로 빈에 등록
    public WebSecurityCustomizer configure(){
        return web -> web.ignoring()
                .requestMatchers("/static/**") // 기본경로 : src/main/java/resource
                                                            // 추후 설정할 정적자원 저장 경로에 보안 해제
                .dispatcherTypeMatchers(DispatcherType.FORWARD);    // MVC방식에서 뷰탄 파일을 로딩하는것을 보안 범위에서 해제

    }

    // http 요청에 대한 웹 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        6.1 이전 버전
        return http
                .authorizeHttpRequests()  // 인증, 인가 설정 시작부
                .requestMatchers("/login", "/signup", "/user")
                .permitAll()    // 위 경로들은 인증 없이 접속 가능
                .anyRequest()   // 위에 적힌 경로 말고는
                .authenticated()  //로그인 필수다
                .and()
                .formLogin()    // 로그인 폼으로 로그인 제어
                .loginPage("/login")    // 로그인 페이지로 지정할 주소
                .defaultSuccessUrl("/blog/list")    // 로그인 하면 처음으로 보여질 페이지
                .and()
                .logout()   // 로그 아웃 관련 설정
                .logoutSuccessUrl("/login") // 로그아웃 성공시 넘어가는 경로
                .invalidateHttpSession(true)    // 로그 아웃하면 다음 접속시 로그인 풀려있게 설정
                .and()
                .csrf() // csrf 공격 방지용 토큰
                .disable()  // 을 쓰지 않겠다.
                .build();
        */
        // 6.1 이후 버전
        return http
                .authorizeHttpRequests(authenticationConfig -> {
                    authenticationConfig.requestMatchers( "/login", "/signup", "/user")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .formLogin(formLoginConfig ->{
                    formLoginConfig.loginPage("/login") // 폼에서 날려준 정보를 토대로 처리를 해주는 주소(post)
                        .defaultSuccessUrl("/blog/list");
                })
                .logout(logoutConfig -> {
                    logoutConfig
                            //.logoutUrl("loglogout") // 디폴트가 /logout으로 잡아주기 때문에 다른 걸로 변경하기는 방법
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true);
                })
                .csrf(csrfConfig -> {
                    csrfConfig.disable();
                })
                .build();
    }

    // 위의 설절을 따라가는 인증은 어떤 서비스 클래스를 통해 설정할것인가
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService)
            throws Exception{
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService) // userService에 기술된 내용을 토대로 로그인 처리
                .passwordEncoder(bCryptPasswordEncoder); // 비밀번호 암호화 저장 모듈
        return builder.build();
    }

    // 암호화 모듈 임포트
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}