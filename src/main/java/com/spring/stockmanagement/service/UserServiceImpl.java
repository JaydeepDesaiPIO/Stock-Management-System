package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser() {
        User user=new User();
        user.setName("jaydeep");
        user.setAddress("pune");
        user.setEmail("jai@mail.com");
        user.setPassword("uygvsbnmdkeiu");
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User update(User user, int id) {
        User existingUser=userRepository.findById(id).get();
        existingUser.setAddress(user.getAddress());
       return userRepository.save(existingUser);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public boolean userExists(String name) {
        Optional<User> byName = userRepository.findByName(name);
        if(byName.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean userExistByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void isUserValid(User user, BindingResult bindingResult) {
        if(user!=null)
        {
            if(user.getName().isBlank() )
            {
                bindingResult.addError(new FieldError("user", "name", "Username can not be blank"));
            }
            if(user.getName()!=null && !user.getName().matches("^[a-zA-Z]+$"))          //&& user.getName().length()<4
            {
                bindingResult.addError(new FieldError("user", "name", "Username can only have letters and contains atleast 4 characters"));
            }
            if(user.getEmail().isBlank())
            {
                bindingResult.addError(new FieldError("user", "email", "Email can not be blank"));
            }
            if(user.getAddress().isBlank())
            {
                bindingResult.addError(new FieldError("user", "address", "Address can not be blank"));
            }
            if(user.getAddress()!=null && !user.getAddress().matches("^[0-9a-zA-Z\\s,-]+$"))
            {
                bindingResult.addError(new FieldError("user", "address", "Please write correct address"));
            }
            if(user.getPassword().isBlank())
            {
                bindingResult.addError(new FieldError("user", "password", "Password can not be blank"));
            }
            if(user.getPassword()!=null && !user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$"))
            {
                bindingResult.addError(new FieldError("user", "password", "Password must contain atleast one number, one UpperCase letter, one LowerCase letter, one Special Character and password length must 8-15 character"));
            }
            if(userExistByEmail(user.getEmail()))
            {
                bindingResult.addError(new FieldError("user","email","Email already in use"));
            }
        }
    }

}
