package com.spring.stockmanagement;

import com.spring.stockmanagement.Enum.Role;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockManagementSystemApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(StockManagementSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
