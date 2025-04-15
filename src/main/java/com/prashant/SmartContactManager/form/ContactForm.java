package com.prashant.SmartContactManager.form;

import org.springframework.web.multipart.MultipartFile;

import com.prashant.SmartContactManager.validator.ValidFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ContactForm {

    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid email address")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String phoneNumber;
    
    @NotBlank(message = "Address is required")
    private String address;
   
    private String description;
    private boolean favourite;
    private String websiteLink;
    private String linkedIn;
    private String id;
    

    // annotation create karenge to validate the file
    //like file size, file type, resolution etc
    @ValidFile
    private MultipartFile contactImage;

    private String picture;

    


}
