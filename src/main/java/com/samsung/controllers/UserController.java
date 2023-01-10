package com.samsung.controllers;

import com.samsung.entities.User;
import com.samsung.models.UserModel;
import com.samsung.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserModel userModel){
        return new ResponseEntity<User>(userService.createUser(userModel), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> readUser(@PathVariable Long id){
        return new ResponseEntity<User>(userService.read(id),HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserModel userModel,@PathVariable Long id){
        return new ResponseEntity<User>(userService.update(userModel,id),HttpStatus.OK);
    }
}
