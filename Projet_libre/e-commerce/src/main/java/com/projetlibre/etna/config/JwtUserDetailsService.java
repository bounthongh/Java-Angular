package com.projetlibre.etna.config;

import com.projetlibre.etna.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.projetlibre.etna.model.JwtUserDetails;
import com.projetlibre.etna.model.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User  user = userRepository.findByUsername(username);
        if (user != null) {
            return new JwtUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
