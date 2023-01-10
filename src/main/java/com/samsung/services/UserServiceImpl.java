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

    @Override
    public User update(UserModel userModel, Long id) {
        User user = read(id);
        user.setName(userModel.getName()!=null ? userModel.getName() : user.getName());
        user.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : user.getEmail());
        user.setPassword(userModel.getPassword()!=null ? userModel.getPassword() : user.getPassword());
        user.setAge(userModel.getAge()!=null ? userModel.getAge() : user.getAge());
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = read(id);
        userRepository.delete(user);
    }
}
