package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.Company;
import org.springframework.validation.BindingResult;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface CompanyService {

    void saveCompany(Company company, Principal principal);

    Optional<Company> checkCompany(String name);

    Optional<Company> findCompanyByContact(String contactNo);

    void validateCompany(Company company, BindingResult bindingResult);

    List<Company> getAllCompany();
}
