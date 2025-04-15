package com.prashant.SmartContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.prashant.SmartContactManager.Entity.User;
import com.prashant.SmartContactManager.form.UserForm;
import com.prashant.SmartContactManager.helper.Message;
import com.prashant.SmartContactManager.helper.MessageType;
import com.prashant.SmartContactManager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String requestMethodName() {
        return "redirect:/home";
    }
    

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

    @RequestMapping("/contact")
    public String contact(){
        System.out.println("Contact page loading...");
        return "contact";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        UserForm userform = new UserForm();
        //userform.setName("Prashant");
        model.addAttribute("userForm", userform);
        System.out.println("Signup page loading...");
        return "signup";
    }

    @RequestMapping("/login")
    public String login(){      
        System.out.println("Login page loading...");
        return "login";
    }

    //from signup page action is calling this method to save the data
    @RequestMapping(value="/do-register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult  ,HttpSession session){
        System.out.println("processing registration");
        // here we have to register the user, send verifiaction mail 

        //1 fetch form data
        System.out.println(userForm);

        //2 validate form data
        if(rBindingResult.hasErrors()){
            return "signup";
        }

        //save to DB
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://drive.google.com/file/d/1ac2RXQp7hboL6DhkOmqKc3Rz8-5FReTR/view?usp=sharing")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://drive.google.com/file/d/1ac2RXQp7hboL6DhkOmqKc3Rz8-5FReTR/view?usp=sharing");
        User saveUser = userService.saveUser(user);
        System.out.println("Registration sucessful");
        //message ="Registratin sucessful"
        Message message = Message.builder().content("Registration successful").type(MessageType.blue).build();
        session.setAttribute("message", message);

        //redirect login page 
        return "redirect:/signup";
    }

}
