package com.prashant.SmartContactManager.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){
        
       
        // agar hamne email and password se login kiya h toh email kaise nikalenge 
       
        if(authentication instanceof OAuth2AuthenticationToken){

            var  oAuth2AuthenticationToken= (OAuth2AuthenticationToken) authentication;
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oAuth2User = (OAuth2User)authentication.getPrincipal();
            String username="";
            // agar google use kiya h toh kaise nikalenge
            if(clientId.equalsIgnoreCase("google")){
                System.out.println("getting google id");
                username = oAuth2User.getAttribute("email").toString();

                
            }else if (clientId.equalsIgnoreCase("github")){
                //agar sign in with GitHub
                System.out.println("Getting github id ");
                username = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email" ) : oAuth2User.getAttribute("login") +"@gmail.com";
            } 
            return username;
        }       
        else{
                // wo user normal h 
                System.out.println("getting data from local database");
                return authentication.getName();
        }
        
        
    }
}
