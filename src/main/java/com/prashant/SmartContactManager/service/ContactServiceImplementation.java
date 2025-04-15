package com.prashant.SmartContactManager.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prashant.SmartContactManager.DAO.ContactRepositories;
import com.prashant.SmartContactManager.DAO.UserRepository;
import com.prashant.SmartContactManager.Entity.Contact;
import com.prashant.SmartContactManager.Entity.User;
import com.prashant.SmartContactManager.helper.ResourceNotFoundException;

@Service
public class ContactServiceImplementation implements ContactService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepositories contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactid = UUID.randomUUID().toString();
        contact.setId(contactid);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        var contactOld = contactRepo.findById(contact.getId()).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id "+contact.getId()));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setFavourite(contact.isFavourite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedIn(contact.getLinkedIn());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
        return contactRepo.save(contactOld);
    }


    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
       return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id "+id)); 

    }

    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id "+id)); 
        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserID(userId);
    }

    @Override
    public List<Contact> getByUser(User user) {
        // Sorting contacts by name before returning 
        // List<Contact> contacts = contactRepo.findByUser(user);
        // contacts.sort(Comparator.comparing(Contact::getName));
        // return contacts;

        //findByUserOrderByName is a custom query method in ContactRepositories which sorts the contacts by name
        return contactRepo.findByUserOrderByName(user);
    }

    // @Override
    // public List<Contact> searchByName(String nameKeyword) {
    //     return contactRepo.findByNameContaining(nameKeyword);
    // }

    //@Override
    // public List<Contact> searchByEmail(String emailKeyword) {
    //     return contactRepo.findByEmailContaining(emailKeyword);
    // }

    // @Override
    // public List<Contact> searchByPhone(String phoneNumber) {
    //     return contactRepo.findByPhoneNumberContaining(phoneNumber);
    // }

    @Override
    public List<Contact> searchByName(String nameKeyword, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        return contactRepo.findByNameContainingIgnoreCaseAndUser(nameKeyword, user);
    }

    @Override
    public List<Contact> searchByEmail(String emailKeyword, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        return contactRepo.findByEmailContainingAndUser(emailKeyword, user);
    }

    @Override
    public List<Contact> searchByPhone(String phoneNumber, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        return contactRepo.findByPhoneNumberContainingAndUser(phoneNumber, user);
    }

    @Override
    public void deleteContactById(String contactId) {
        contactRepo.deleteById(contactId);
    }


} 
