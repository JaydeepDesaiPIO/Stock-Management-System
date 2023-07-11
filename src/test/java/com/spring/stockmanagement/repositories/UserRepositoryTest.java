package com.spring.stockmanagement.repositories;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CompanyRepository companyRepository;

    @Test
    void saveUser() {

        Company c= Company.builder()
                .companyName("L").build();
        companyRepository.save(c);

        User user = User.builder()
                .company(c)
                .address("A").build();
                userRepository.save(user);
    }
}