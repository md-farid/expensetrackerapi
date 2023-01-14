package com.samsung.controllers;

import com.samsung.entities.User;
import com.samsung.models.UserModel;
import com.samsung.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> readUser(){
        return new ResponseEntity<User>(userService.read(),HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUser(@RequestBody UserModel userModel){
        return new ResponseEntity<User>(userService.update(userModel),HttpStatus.OK);
    }

    @DeleteMapping("/deactive")
    public ResponseEntity<HttpStatus> deleteUser(){
        userService.delete();
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
