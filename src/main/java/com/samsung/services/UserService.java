package com.samsung.services;

import com.samsung.entities.User;
import com.samsung.models.UserModel;

public interface UserService {
    User createUser(UserModel user);
    User read(Long id);
    User update(UserModel userModel, Long id);
    void delete(Long id);
    User getLoggedInUser();
}
