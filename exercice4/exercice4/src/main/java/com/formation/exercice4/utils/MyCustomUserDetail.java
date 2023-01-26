package com.formation.exercice4.utils;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.formation.exercice4.metier.Utilisateur;

public class MyCustomUserDetail implements UserDetails{

    private Utilisateur u;

    public MyCustomUserDetail(Utilisateur u) {
        this.u = u;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getPassword() {
       return u.getPassword();
    }

    @Override
    public String getUsername() {
        return u.getNom();
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

    @Override
    public boolean isEnabled() {
      return u.isEnabled();
    }
    
}
