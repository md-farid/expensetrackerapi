package com.samsung.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserModel {
    @NotBlank(message = "Please enter your name.")
    private String name;

    @NotNull(message = "Please enter your email.")
    @Email(message = "Please enter valid email.")
    private String email;

    @NotNull(message = "Please enter your password.")
    @Size(min = 5, message = "Password should be at least 5 characters.")
    private String password;

    private Long age = 0L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
