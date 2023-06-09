package com.spring.blog.controller;

import com.spring.blog.dto.BmiDTO;
import com.spring.blog.dto.PersonDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Scanner;

//@Controller   //컨트롤러 지정
//@ResponseBody    //REST형식 변환, 메서드 위에 붙으면 해당 메서드를 REST형식으로
@RestController // 위 2개 어노테이션을 한번에 지정해줌
@RequestMapping("/resttest")
@CrossOrigin(origins = "http://127.0.0.1:5500") //해당 출처의 비동기 요청 허용
public class RestApiController {
    // REST 컨트롤러는 크게 json을 리턴하거나, String을 리턴하게 만들 수 있다
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hell(){
        return "안녕하세요";
    }

    // 문자배열도 리턴할 수 있다
    @RequestMapping(value = "/foods",method = RequestMethod.GET)
    public List<String> foods(){
        List<String> foodList = List.of("탕수육","양장피","짜장면");
        return foodList;
    }

    @RequestMapping(value = "/person",method = RequestMethod.GET)
    public PersonDTO person(){
        PersonDTO p = PersonDTO.builder()
                .id(1L)
                .name("박상현")
                .age(23)
                .build();
        return p;
    }

    // ResponseEntity는 상태 코드까지 함께 리턴
    @GetMapping("/person-list")
    public ResponseEntity<?> personList(){
        PersonDTO p = PersonDTO.builder().id(1L).name("박자바").age(12).build();
        PersonDTO p2 = PersonDTO.builder().id(2L).name("김자바").age(23).build();
        PersonDTO p3 = PersonDTO.builder().id(3L).name("최자바").age(34).build();

        List<PersonDTO> personDTOList = List.of(p,p2,p3);

        //ok()는 200코드를 반환, body에 실제 리턴값 입력
        return ResponseEntity.ok().body(personDTOList);
    }

    @RequestMapping(value = "/bmi", method = RequestMethod.GET)
    public ResponseEntity<?> bmi(BmiDTO bmi){
        if (bmi.getWeight() <= 0 || bmi.getHeight() <= 0){
            return ResponseEntity.badRequest().body("값을 다시 입력하세요.");
        }
        double result = bmi.getWeight() / Math.pow(((bmi.getHeight()/100)),2);

        //헤더 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("lunch", "pizza");

        return ResponseEntity
                .ok()   //200
                .headers(headers)   //헤더 추가
                .body(result);  //사용자에게 보여질 데이터
    }

    //JSON 형태로 받아온다
    @RequestMapping(value = "/bmi2", method = RequestMethod.GET)
    public ResponseEntity<?> bmi2(@RequestBody BmiDTO bmi){
        if (bmi.getWeight() <= 0 || bmi.getHeight() <= 0){
            return ResponseEntity.badRequest().body("값을 다시 입력하세요.");
        }
        double result = bmi.getWeight() / Math.pow(((bmi.getHeight()/100)),2);

        //헤더 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("lunch", "pizza");

        return ResponseEntity
                .ok()   //200
                .headers(headers)   //헤더 추가
                .body(result);  //사용자에게 보여질 데이터
    }
}
