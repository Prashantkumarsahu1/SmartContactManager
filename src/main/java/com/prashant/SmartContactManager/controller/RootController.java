package com.prashant.SmartContactManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.prashant.SmartContactManager.Entity.User;
import com.prashant.SmartContactManager.helper.Helper;
import com.prashant.SmartContactManager.service.UserService;

//This is the root controller which runs for every request for request link  @ModelAttribute is used to do this
@ControllerAdvice
public class RootController {    


    private Logger logger = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService userService;
    
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication ){

        if(authentication == null){
            return;
        }

        System.out.println("-----Adding logged in user information to the model-----"); 

        // when user is logging in one id is getting name for self -email , Github - numeric id(191899534)......
        // printing those id 
        String userName = Helper.getEmailOfLoggedInUser(authentication);
        //System.out.println("Username retrieved from authentication: " + userName);
        // fetch data from DB with name
        User user = userService.getUserByEmail(userName);
       
            System.out.println(user.getName());
            System.out.println(user.getEmail());
            logger.info("User logged in :{} ",userName);
            model.addAttribute("loggedInUser", user);  
            
    }
}
