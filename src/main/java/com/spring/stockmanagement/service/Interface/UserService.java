package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.User;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User addUser(User user);
    public List<User> getAllUsers();
    User findById(int id);
    User update(User user, int id);
    Optional<User> findByName(String name);

    boolean userExists(String name);

    Optional<User> findByEmail(String email);

    boolean userExistByEmail(String email);

    void isUserValid(User user, BindingResult bindingResult);
}
