package com.spring.blog.controller;

import com.spring.blog.entity.User;
import com.spring.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    UsersService usersService;

    @Autowired
    public UserController(UsersService usersService){
        this.usersService = usersService;
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
}
