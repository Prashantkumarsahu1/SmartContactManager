package com.prashant.SmartContactManager.controller;



import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.prashant.SmartContactManager.Entity.Contact;
import com.prashant.SmartContactManager.Entity.User;
import com.prashant.SmartContactManager.form.ContactForm;
import com.prashant.SmartContactManager.helper.Helper;
import com.prashant.SmartContactManager.helper.Message;
import com.prashant.SmartContactManager.helper.MessageType;
import com.prashant.SmartContactManager.service.ContactService;
import com.prashant.SmartContactManager.service.ImageService;
import com.prashant.SmartContactManager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    // add contact page: handler

    @RequestMapping("/add")
    
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        //contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        //System.out.println("Contact form: " + contactForm);
        return "user/add_contact";
    }

    //Add contact form: handler

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {

                

        // process the form data

        // 1 validate form
        

        if (result.hasErrors()) {   
            
            System.out.println("Error encountered");
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            
            session.setAttribute("message",Message.builder()
                    .content("Please correct the errors in the form and try again.")
                    .type(MessageType.red) // Set a different type for error
                    .build());

            return "user/add_contact";
        }

        

        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> contact

        User user = userService.getUserByEmail(username);
        // To process the contact picture

        // image process

        logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename());



        


        Contact contact = new Contact();
        
        contact.setName(contactForm.getName());
        contact.setFavourite(contactForm.isFavourite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedIn(contactForm.getLinkedIn());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        // uplod karne ka code
        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {            
        
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
        }

        
        contactService.save(contact);
        System.out.println(contactForm);

        session.setAttribute("message", Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts/add";

    }

    
    // show contacts
    @RequestMapping("/contactList")
    public String viewContactList(Model model, Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        List<Contact>  contact= contactService.getByUser(user);
        model.addAttribute("contacts", contact);

        for (Contact c : contact) {
            System.out.println(c.getName() + " - " + c.getEmail() + " - " + c.getPhoneNumber());
        }

        return "user/contactList";
    }


    //search handler
    @RequestMapping("/search")
    
    public String searchHandler( @RequestParam("field") String field, @RequestParam("keyword") String value, Model model, Authentication authentication) {


        logger.info("fiels {} keyword {} Login {}", field,value,Helper.getEmailOfLoggedInUser(authentication));
        System.out.println("_".repeat(20));

        if(field.equals("name")) {
            List<Contact> contacts = contactService.searchByName(value, Helper.getEmailOfLoggedInUser(authentication));            
            System.out.println(contacts.toString());
            model.addAttribute("contacts", contacts);
        } else if(field.equals("email")) {
            List<Contact> contacts = contactService.searchByEmail(value, Helper.getEmailOfLoggedInUser(authentication));
            System.out.println(contacts);
            model.addAttribute("contacts", contacts);
        } else if(field.equals("phone")) {
            List<Contact> contacts = contactService.searchByPhone(value, Helper.getEmailOfLoggedInUser(authentication));
            System.out.println(contacts);
            model.addAttribute("contacts", contacts);
        }

        // Add field and keyword to the model
            model.addAttribute("field", field);
            model.addAttribute("keyword", value);
        //logger.info("fiels {} keyword {} Login {}", field,value,Helper.getEmailOfLoggedInUser(authentication));
        return "user/search";
    }

    // delete contact handler
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId) {
        contactService.deleteContactById(contactId);
        logger.info("Contact with id {} deleted", contactId);

       return "redirect:/user/contacts/contactList";
    }

    // update contact handler
    @RequestMapping("/updateview/{contactId}")
    public String updateContact(@PathVariable("contactId") String contactId, Model model) {
        Contact contact = contactService.getById(contactId);
        System.out.println("Contact details"+contact);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setLinkedIn(contact.getLinkedIn());
        contactForm.setWebsiteLink(contact.getWebsiteLink());            
        contactForm.setPicture(contact.getPicture());
        contactForm.setId(contact.getId());
        //contactForm.setContactImage(contact.getContactImage());
        model.addAttribute("contactForm", contactForm);
        System.out.println("Contact details --->"+contactForm);            
        return "user/update_view";
    }


    @RequestMapping(value = "/saveUpdate/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,  @Valid @ModelAttribute ContactForm contactForm, BindingResult result,Model model) {

        if (result.hasErrors()) {
            System.out.println("Error encountered");
            
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            return "user/update_view";
        }
        //update the data
        Contact con = contactService.getById(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavourite(contactForm.isFavourite());
        con.setLinkedIn(contactForm.getLinkedIn());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        

        //process the image if choosen
        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String imageURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            con.setCloudinaryImagePublicId(filename);
            con.setPicture(imageURL);            
            
            
            System.out.println("%%%%%%%Image uploaded successfully%%%%%%%%%%");
            System.out.println(imageURL);
        }
         
        // con.setCloudinaryImagePublicId(contactForm.getPicture());

        contactService.update(con);
        Message message = Message.builder()
                .content("Contact updated successfully")
                .type(MessageType.green)
                .build();
        model.addAttribute("message", message);


        
        return "redirect:/user/contacts/contactList";
    }

    

}

