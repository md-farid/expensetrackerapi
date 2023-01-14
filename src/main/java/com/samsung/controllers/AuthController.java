package com.samsung.controllers;

import com.samsung.entities.User;
import com.samsung.models.JWTResponse;
import com.samsung.models.LoginModel;
import com.samsung.models.UserModel;
import com.samsung.security.CustomUserDetailsService;
import com.samsung.services.UserService;
import com.samsung.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserModel userModel){
        return new ResponseEntity<User>(userService.createUser(userModel), HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<JWTResponse> login(@RequestBody LoginModel loginModel) throws Exception {

        authenticate(loginModel.getEmail(),loginModel.getPassword());

        // we need to generate jwt token
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginModel.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new ResponseEntity<JWTResponse>(new JWTResponse(token),HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        }catch (DisabledException ex){
            throw new Exception("User disabled.");
        }catch (BadCredentialsException ex){
            throw new Exception("Bad credentials.");
        }

    }
}
