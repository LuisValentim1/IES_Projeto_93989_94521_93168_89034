package com.ies.blossom.security;

import java.sql.Timestamp;

import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found");

        user.setLastJoined(new Timestamp(System.currentTimeMillis()));
        this.userRepository.save(user);

        return new CustomUserDetails(user);
    }
    
}
