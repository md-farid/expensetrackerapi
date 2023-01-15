package com.samsung.config;

import com.samsung.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ProjectSecurityConfiguration {

    @Bean
    JwtRequestFilter jwtRequestFilter(){
        return new JwtRequestFilter();
    }

    @Bean
    SecurityFilterChain defSecurityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth","/register").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        http.formLogin();
        http.httpBasic();
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
    /**
    @Bean
    InMemoryUserDetailsManager userDetailsManager(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager =
                new InMemoryUserDetailsManager();

        UserDetails user = User.withUsername("user").password("12345").authorities("user").build();
        UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
        inMemoryUserDetailsManager.createUser(user);
        inMemoryUserDetailsManager.createUser(admin);
        return inMemoryUserDetailsManager;
    }*/

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
