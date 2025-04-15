package com.prashant.SmartContactManager.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.prashant.SmartContactManager.Entity.Contact;
import com.prashant.SmartContactManager.Entity.User;

@Repository
public interface ContactRepositories extends JpaRepository<Contact,String>{

    //find the contact by user

    //Custom finder method
    List<Contact> findByUserOrderByName(User user);

    // custom query method
    @Query("Select c from Contact c Where c.user.id = :userid")
    List<Contact> findByUserID(@Param("userid") String userid);

    //List<Contact> findByNameContaining(String nameKeyword);
    List<Contact> findByEmailContaining(String emailKeyword);
    List<Contact> findByPhoneNumberContaining(String phoneNumber);



    List<Contact> findByNameContainingIgnoreCaseAndUser(String name, Optional<User> user);
    List<Contact> findByEmailContainingAndUser(String email, Optional<User> user);
    List<Contact> findByPhoneNumberContainingAndUser(String phoneNumber, Optional<User> user);

    void deleteById(String contactId);
}
