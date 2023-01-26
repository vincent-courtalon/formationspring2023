package com.formation.exercice4.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formation.exercice4.metier.Utilisateur;
import com.formation.exercice4.repositories.UserRepository;

//@Service
public class MyCustomUserDetailsService  implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> u = userRepository.findById(username);
        if (!u.isPresent())
            throw new UsernameNotFoundException("utilisateur inconnu");
        return new MyCustomUserDetail(u.get());
    }
    
}
