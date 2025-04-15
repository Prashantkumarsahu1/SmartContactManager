package com.prashant.SmartContactManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    // user Dashboard page
    @RequestMapping("/dashboard")    
    public String dashBoard(){
        return "user/dashboard";
    }

    //user profile page
    @RequestMapping("/profile")    
    public String profile(){
        return "user/profile";
    }



}
