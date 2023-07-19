package com.spring.stockmanagement.service.Interface;

import com.spring.stockmanagement.entities.Company;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    List<Company> getAllCompany();

    Optional<Company> findByCompanyName(String name);

    Company saveCompany(Company company);

    void isCompanyValid(Company company, BindingResult bindingResult);
}
