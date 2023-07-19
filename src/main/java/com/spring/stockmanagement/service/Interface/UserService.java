package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.User;

import java.util.List;

public interface UserService {

    public User addUser(User user);
    public List<User> getAllUsers();
    User findById(int id);

    User update(User user, int id);
}
