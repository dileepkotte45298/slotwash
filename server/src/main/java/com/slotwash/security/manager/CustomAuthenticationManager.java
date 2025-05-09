package com.slotwash.security.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.slotwash.security.provider.CustomAuthenticationProvider;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if(authentication.getPrincipal()==null || authentication.getCredentials()==null){
            throw  new BadCredentialsException("Please Provide Username or Password");
        }

        if(customAuthenticationProvider.supports(authentication.getClass())){
          Authentication auth = customAuthenticationProvider.authenticate(authentication);
          return auth;
        }

        throw new BadCredentialsException("Exception! Username or Password Incorrect");
    }
}

