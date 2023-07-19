package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.Company;
import org.springframework.validation.BindingResult;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company saveCompany(Company company);

    Optional<Company> checkCompany(String name);

    void validateCompany(Company company, BindingResult bindingResult);

    List<Company> getAllCompany();
}
