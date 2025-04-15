package com.prashant.SmartContactManager.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prashant.SmartContactManager.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    //find user details by id
    Optional<User>  findByEmail(String email);

    //DB related operation 
    //custom query
    //extra method
}
