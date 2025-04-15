package com.prashant.SmartContactManager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.prashant.SmartContactManager.service.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
    @Autowired
    private SecurityCustomUserDetailService securityCustomUserDetailService;
    @Autowired
    private OAuthenticationSuccessHandler oAuthenticationSuccessHandler;
    //Configuration of Authentication provider, taking data from DB and verifying
   @Bean
   public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // user detail service ka object
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);

        // user detail password ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    // login time verification and url protection from unauthorised access
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // URL configuration
        httpSecurity.authorizeHttpRequests(authorize ->{
            //authorize.requestMatchers("/home","/signup","/service" ).permitAll();
            //user based url should be authenticated 
            authorize.requestMatchers("/user/**").authenticated();

            //remaining all url shoul be public
            authorize.anyRequest().permitAll();
        });

        httpSecurity.formLogin(formLogin ->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");            
           // formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email"); // don't forget to give token name (email) in login html 
            formLogin.passwordParameter("password");
           
            /* 
            formLogin.failureHandler(new AuthenticationFailureHandler() {

                @Override
                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException exception) throws IOException, ServletException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
                }
                
            });

            formLogin.successHandler(new AuthenticationSuccessHandler() {

                @Override
                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                        Authentication authentication) throws IOException, ServletException {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
                }
                
            });

            */
        });
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.logout(logout -> {
            logout.logoutUrl("/logout")  // URL to trigger logout
                  .logoutSuccessUrl("/login?logout=true")  // Redirect after successful logout
                  .invalidateHttpSession(true)  // Invalidate the session
                  .deleteCookies("JSESSIONID");  // Delete cookies
        });
        
       httpSecurity.oauth2Login(oauth ->{
        oauth.loginPage("/login");
        oauth.successHandler(oAuthenticationSuccessHandler);
       });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

