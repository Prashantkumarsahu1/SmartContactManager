package com.prashant.SmartContactManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PageController {
    @RequestMapping("/home")
    public String home(Model model){        
        System.out.println("We are in Home page");
        model.addAttribute("Name","Prashant");
        model.addAttribute("Linkedin", "https://www.linkedin.com/in/prashant-kumar-sahu/");
        model.addAttribute("Project", "Smart contact manager");
        model.addAttribute("ProjectType", "Spring project");
        
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("isLogin",true);
        System.out.println("About page loading...");
        return "about";
    }
    @RequestMapping("/service")
    public String services(){
        System.out.println("service page loading...");
        return "service";
    }

}
