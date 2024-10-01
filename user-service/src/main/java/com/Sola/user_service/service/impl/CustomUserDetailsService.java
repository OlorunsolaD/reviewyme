package com.Sola.user_service.service.impl;
//import com.Sola.user_service.Config.CustomUserDetails;
import com.Sola.user_service.model.UserEntity;
import com.Sola.user_service.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity;
        userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(" User not found with email: "  + email));
//        return new CustomUserDetails(users);

        Set<SimpleGrantedAuthority> authorities = userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                authorities

        );
    }
}
