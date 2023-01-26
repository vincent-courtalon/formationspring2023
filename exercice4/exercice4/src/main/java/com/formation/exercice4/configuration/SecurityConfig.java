package com.formation.exercice4.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // configuration de l'authentification
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails user = User.withUsername("vincent")
                                .password(passwordEncoder.encode("toto!"))
                                .roles("USER")
                                .build();
        UserDetails admin = User.withUsername("admin")
                                .password(passwordEncoder.encode("root!"))
                                .roles("USER", "ADMIN")
                                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    // configuration des autorisations
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // SessionCreationPolicy.STATELESS -> plus de session, et donc plus de cookie jsessionId
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests().antMatchers("/todolist/**").hasRole("ADMIN")
        .and()
            .authorizeRequests().antMatchers("/tache/**").hasAnyRole("USER", "ADMIN")
        .and()
            .authorizeRequests().antMatchers("/**").hasAnyRole("ADMIN")
        .and()
            .httpBasic()
        .and()
            .csrf().disable(); // desactive la protection csrf (inutile pour API rest pure)
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
