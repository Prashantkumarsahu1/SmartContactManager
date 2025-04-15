package com.prashant.SmartContactManager.service;

import java.util.List;
import java.util.Optional;

import com.prashant.SmartContactManager.Entity.User;


public interface UserService {

// Add business logic releated to user

   User saveUser (User user);

   Optional<User> getUserById(String id);

   Optional<User> updateUser(User user);

   void deleteUser(String id);

   boolean isUserExist(String userId);

   boolean isUserEmailExist(String email);

   List<User> getAllUser();

   User getUserByEmail(String email);





   //add more method releated to Business logic  

}
