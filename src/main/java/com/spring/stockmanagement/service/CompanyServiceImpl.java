package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.repositories.CompanyRepository;
import com.spring.stockmanagement.service.Interface.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findByCompanyName(String name) {
        return companyRepository.findByCompanyName(name);
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void isCompanyValid(Company company, BindingResult bindingResult) {

        if(company!=null)
        {
            if(company.getCompanyName().isBlank()){
                bindingResult.addError(new FieldError("company","companyName","Company name can not be blank"));
            }
            if(findByCompanyName(company.getCompanyName()).isPresent()) {
                bindingResult.addError(new FieldError("company","companyName","Company already exist"));
            }
        }
    }
}
