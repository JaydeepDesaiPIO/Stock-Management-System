package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.CompanyRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> checkCompany(String name) {
        return companyRepository.findByCompanyName(name);
    }

    @Override
    public void validateCompany(Company company, BindingResult bindingResult) {
        if(company!=null)
        {
            if(company.getCompanyName().isBlank()){
                bindingResult.addError(new FieldError("company","companyName","Company name cannot be blank"));
            }
            if(checkCompany(company.getCompanyName()).isPresent()){
                bindingResult.addError(new FieldError("company","companyName","Company already exist"));
            }
            if(company.getContactNo().isBlank()){
                bindingResult.addError(new FieldError("company", "contactNo", "Contact can not be blank"));
            }
            if(company.getContactNo()!=null && !company.getContactNo().matches("^[0-9].{10}+$")) {
                bindingResult.addError(new FieldError("company", "contactNo", "Contact must be of 10 digits"));
            }
            if(company.getCompanyAddress().isBlank()) {
                bindingResult.addError(new FieldError("company", "companyAddress", "Address can not be blank"));
            }
        }
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }
}
