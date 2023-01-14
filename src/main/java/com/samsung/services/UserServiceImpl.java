package com.samsung.services;

import com.samsung.entities.User;
import com.samsung.exceptions.ItemAlreadyExistsException;
import com.samsung.exceptions.ResourceNotFoundException;
import com.samsung.models.UserModel;
import com.samsung.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserModel userModel) {
        if(userRepository.existsByEmail(userModel.getEmail())){
            throw new ItemAlreadyExistsException("User is already register with email.");
        }
        User user = new User();
        BeanUtils.copyProperties(userModel,user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User read() {
        Long userId = getLoggedInUser().getId();

        return userRepository.findById(userId).orElseThrow(()->{
            throw new ResourceNotFoundException("User not found with id "+userId);
        });
    }

    @Override
    public User update(UserModel userModel) {
        User user = read();
        user.setName(userModel.getName()!=null ? userModel.getName() : user.getName());
        user.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : user.getEmail());
        user.setPassword(userModel.getPassword()!=null ? passwordEncoder.encode(userModel.getPassword()) : user.getPassword());
        user.setAge(userModel.getAge()!=null ? userModel.getAge() : user.getAge());
        return userRepository.save(user);
    }

    @Override
    public void delete() {
        User user = read();
        userRepository.delete(user);
    }


    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(()->{
            throw new UsernameNotFoundException("User not found for the email "+email);
        });
    }
}
