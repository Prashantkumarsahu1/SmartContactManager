package com.prashant.SmartContactManager.service;

import java.util.List;

import com.prashant.SmartContactManager.Entity.Contact;
import com.prashant.SmartContactManager.Entity.User;

public interface ContactService {


    // save contacts
    Contact save(Contact contact);

    //update Contact
    Contact update(Contact contact);

    //get contact
    List<Contact> getAll();

    //get contact by id

    Contact getById(String id);

    // delete contact
    void delete(String id);

    // search contact
    List<Contact> searchByName(String nameKeyword, String userEmail);
    List<Contact> searchByEmail(String emailKeyword, String userEmail);
    List<Contact> searchByPhone(String phoneNumber, String userEmail);

    // get contact by userID
    List<Contact> getByUserId(String userId);

    List<Contact> getByUser(User user);

    void deleteContactById(String contactId);

    



}
