package com.samsung.services;

import com.samsung.entities.User;
import com.samsung.models.UserModel;

public interface UserService {
    User createUser(UserModel user);
    User read();
    User update(UserModel userModel);
    void delete();
    User getLoggedInUser();
}
