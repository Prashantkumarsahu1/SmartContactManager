package com.prashant.SmartContactManager.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserForm {
    @NotBlank(message = "Username is required")
    @Size(min=3, message = "Min 3 character required")
    private String name;

    
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid email address")
    private String email;

    @Size(min = 6,message = "Password must be more than 6 character")
    @NotBlank(message = "Password is required")
    private String password;

    @Size(max = 10,min = 10,message = "Invalid phone number")
    private String phoneNumber;
    
    @NotBlank(message = "About can't be empty")
    private String about;

}
