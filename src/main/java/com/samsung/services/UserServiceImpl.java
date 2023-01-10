package com.samsung.services;

import com.samsung.entities.User;
import com.samsung.exceptions.ItemAlreadyExistsException;
import com.samsung.exceptions.ResourceNotFoundException;
import com.samsung.models.UserModel;
import com.samsung.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(UserModel userModel) {
        if(userRepository.existsByEmail(userModel.getEmail())){
            throw new ItemAlreadyExistsException("User is already register with email.");
        }
        User user = new User();
        BeanUtils.copyProperties(userModel,user);
        return userRepository.save(user);
    }

    @Override
    public User read(Long id) {
        return userRepository.findById(id).orElseThrow(()->{
            throw new ResourceNotFoundException("User not found with id "+id);
        });
    }
}
