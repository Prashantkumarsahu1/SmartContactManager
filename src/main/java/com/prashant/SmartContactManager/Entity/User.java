package com.prashant.SmartContactManager.Entity;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="User")
@Table(name ="users") //this name table will create in DB
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    public static UserDetails withUsername;

    @Id   
    //@GeneratedValue(strategy =  GenerationType.IDENTITY)
    private String userId;

    @Column(name="user_name", nullable=false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(length = 10000)
    private String about;

    @Column(length = 10000)
    //@Getter(value = AccessLevel.NONE) to disable automatic generation of getter and setter via lombok
    private String profilePic;
    
    private String phoneNumber;
    
    //Information
    private boolean enabled =true;
    private boolean emailVerified =false;
    private boolean phoneVerified =false;

    //Login method
    @Enumerated(value =  EnumType.STRING)
    private Provider provider = Provider.SELF;
    private String providerUserID;


    //mapping the user to contact list
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =  FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contactList = new ArrayList<>();



    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the list of roles (String) to a collection of SimpleGrantedAuthority
       Collection<SimpleGrantedAuthority> roles =  roleList.stream()
                                                .map(role -> new SimpleGrantedAuthority(role))
                                                .collect(Collectors.toList());
       return roles;
    }

    // username is our email add in this project
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
