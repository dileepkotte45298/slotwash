package com.slotwash.security.provider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.slotwash.models.User;
import com.slotwash.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired UserRepository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<User> user = userRespository.findById(username);

        if(user.isPresent()){
            if(user.get().isEnabled()){
                UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.get().getUserName(),user.get().getPassword(),user.get().getRoles());
                return  userDetails;
            }
            else{
                throw  new UsernameNotFoundException("Oh! User was deactivated");
            }
        }
        throw  new UsernameNotFoundException("Oh! Username Not Found Exception");
    }
}
