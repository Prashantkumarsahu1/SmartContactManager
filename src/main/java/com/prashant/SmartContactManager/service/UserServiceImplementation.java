package com.prashant.SmartContactManager.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prashant.SmartContactManager.DAO.UserRepository;
import com.prashant.SmartContactManager.Entity.User;
import com.prashant.SmartContactManager.helper.AppConstants;
import com.prashant.SmartContactManager.helper.ResourceNotFoundException;

@Service
public class UserServiceImplementation  implements UserService {
    @Autowired
    private UserRepository userRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;
    // @Autowired
    // private AppConstants appConstants;
    //To save the user
    @Override
    public User saveUser(User user) { 
        String userId = UUID.randomUUID().toString(); 
        user.setUserId(userId); 

        //encode password 
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set Role list
        
        user.setRoleList(List.of(AppConstants.ROLE_USER));


        logger.info(user.getProvider().toString());

        return userRepo.save(user);
    }


    //To fetch user by his id
    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    //To update the user
    @Override
    public Optional<User> updateUser(User user) {
        // if we get the user then return else throw exception
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserID(user.getProviderUserID());

        //now save the info in database
        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    // To delete the user details
    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
         userRepo.delete(user2);
    }

    // fetch all record
    @Override
    public List<User> getAllUser() {
        userRepo.findAll();
        return null;
    }

    
    // Existence check
    @Override
    public boolean isUserEmailExist(String email) {
        User user2 = userRepo.findByEmail(email).orElse(null);
        return user2 != null? true : false;
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 !=null ? true:false;
    }


    @Override
    public User getUserByEmail(String email) {
      return userRepo.findByEmail(email).orElse(null) ; 
    }


 



    

   

        
}
