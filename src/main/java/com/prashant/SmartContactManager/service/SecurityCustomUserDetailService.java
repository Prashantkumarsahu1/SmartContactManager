package com.prashant.SmartContactManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prashant.SmartContactManager.DAO.UserRepository;
@Service
public class SecurityCustomUserDetailService  implements UserDetailsService{
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // apne user ka load krna h and check krna h 
        return userRepo.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found :"+username));
    }

    

}
 