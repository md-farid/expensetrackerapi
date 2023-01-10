package com.samsung.config;

import com.samsung.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfiguration {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    SecurityFilterChain defSecurityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth","/register").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().httpBasic();
        return http.build();
    }

    // 1st approach create multiple users
    /**
    @Bean
    InMemoryUserDetailsManager userDetailsManager(){
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("12345")
                .authorities("admin")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .authorities("user")
                .build();
        return new InMemoryUserDetailsManager(admin,user);
    }*/

    // 2nd approach create multiple users
    @Bean
    InMemoryUserDetailsManager userDetailsManager(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager =
                new InMemoryUserDetailsManager();

        UserDetails user = User.withUsername("user").password("12345").authorities("user").build();
        UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
        inMemoryUserDetailsManager.createUser(user);
        inMemoryUserDetailsManager.createUser(admin);
        return inMemoryUserDetailsManager;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
