package com.prashant.SmartContactManager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.prashant.SmartContactManager.DAO.UserRepository;
import com.prashant.SmartContactManager.Entity.Provider;
import com.prashant.SmartContactManager.Entity.User;
import com.prashant.SmartContactManager.helper.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepo;

    Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthenticationSuccessHandler");

        //To get authorised client registration id
        OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);
              
        //To create default user 
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();       

        //To print all attribute in console 
        // user.getAttributes().forEach((key,value) ->{
        //     logger.info("{} => {}",key,value);
        // });

        //whatever is general attribue assign here 
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setEmailVerified(true);
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEnabled(true);
        user.setProviderUserID(oauthUser.getName());



// for every service provider (Google, Github, facebook, )
    if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
        // google authentication acc to attributes
        user.setName(oauthUser.getAttribute("name").toString());
        user.setEmail(oauthUser.getAttribute("email").toString());
        //user.setAbout(oauthUser.getAttribute("about").toString());
        user.setAbout("This account is created using Google..");
        user.setProfilePic(oauthUser.getAttribute("picture").toString());
        user.setProvider(Provider.GOOGLE);
        
    }else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
        // github authentication acc to attributes
        user.setName(oauthUser.getAttribute("login").toString());
        String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email" ) : oauthUser.getAttribute("login") +"@gmail.com";
        user.setEmail(email);
        user.setProfilePic(oauthUser.getAttribute("avatar_url").toString());
        //user.setAbout(oauthUser.getAttribute("bio").toString());
        user.setAbout("This account is created using GitHub..");
        user.setProvider(Provider.GITHUB);


    }else if(authorizedClientRegistrationId.equalsIgnoreCase("linkein")){
        // linkedln authentication acc to attributes
    }
    else{
        logger.info("OAuthenticationSuccessHandler : Unknown provider");
    }

    User tempUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if(tempUser == null){
            userRepo.save(user);
            System.out.println("user save ");
        }
        
    // after processing, redirection page
    new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
