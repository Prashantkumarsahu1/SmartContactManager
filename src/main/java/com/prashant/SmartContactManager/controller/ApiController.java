package com.prashant.SmartContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.SmartContactManager.Entity.Contact;
import com.prashant.SmartContactManager.service.ContactService;



@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    //get contacts  
     @GetMapping("/contacts/{contactid}")
        public Contact getContacts(@PathVariable("contactid") String contactid) {
            System.out.println("Contact id "+ contactid);
            return contactService.getById(contactid);
        }

}
