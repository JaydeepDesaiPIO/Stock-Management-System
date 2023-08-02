package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.User;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User addUser(User user);
    public List<User> getAllUsers();
    User findById(int id);
    void validateUserForUpdate(User user, int id, BindingResult bindingResult);
    Optional<User> findByName(String name);

    boolean userExists(String name);

    Optional<User> findByEmail(String email);

    boolean userExistByEmail(String email);

    Optional<User> findByContact(String contact);

    boolean userExistByContact(String contact);

    void isUserValid(User user, BindingResult bindingResult);

    void deleteUserById(int id);

    void updateUser(User user, int id);
}
