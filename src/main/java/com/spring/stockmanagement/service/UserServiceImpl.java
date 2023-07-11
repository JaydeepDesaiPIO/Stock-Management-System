package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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
}
