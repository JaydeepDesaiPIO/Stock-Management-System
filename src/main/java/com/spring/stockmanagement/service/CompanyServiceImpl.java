package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.User;
import com.spring.stockmanagement.repositories.CompanyRepository;
import com.spring.stockmanagement.repositories.UserRepository;
import com.spring.stockmanagement.service.Interface.CompanyService;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveCompany(Company company, Principal principal) {
        companyRepository.save(company);
        String currentUserName=principal.getName();
        User CurrentUser=userRepository.findByName(currentUserName).get();
        CurrentUser.setCompany(company);
        userRepository.save(CurrentUser);
    }

    @Override
    public Optional<Company> checkCompany(String name) {
        return companyRepository.findByCompanyName(name);
    }

    @Override
    public Optional<Company> findCompanyByContact(String contactNo) {
        return companyRepository.findByContactNo(contactNo);
    }

    @Override
    public void validateCompany(Company company, BindingResult bindingResult) {
        if(company!=null)
        {
            if(StringUtils.isBlank(company.getCompanyName())){
                bindingResult.addError(new FieldError("company","companyName","Company name cannot be blank"));
            }
            if(checkCompany(company.getCompanyName()).isPresent()){
                bindingResult.addError(new FieldError("company","companyName","Company already exist"));
            }
            if(StringUtils.isBlank(company.getContactNo())){
                bindingResult.addError(new FieldError("company", "contactNo", "Contact can not be blank"));
            }
            if(company.getContactNo()!=null && !company.getContactNo().matches("^[0-9].{9}+$")) {
                bindingResult.addError(new FieldError("company", "contactNo", "Contact must be of 10 digits"));
            }
            if(StringUtils.isBlank(company.getCompanyAddress())){
                bindingResult.addError(new FieldError("company", "companyAddress", "Address can not be blank"));
            }
            if(findCompanyByContact(company.getContactNo()).isPresent()){
                bindingResult.addError(new FieldError("company", "contactNo", "Contact already in use"));
            }
        }
    }

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public void validateCompanyForUpdate(int id, Company company, BindingResult bindingResult) {
        Company existingCompany=companyRepository.findById(id).get();
        if(company!=null) {
            if (StringUtils.isBlank(company.getCompanyName())) {
                bindingResult.addError(new FieldError("company", "companyName", "Company name cannot be blank"));
            }
            if (!existingCompany.getCompanyName().equalsIgnoreCase(company.getCompanyName()) && checkCompany(company.getCompanyName()).isPresent()) {
                bindingResult.addError(new FieldError("company", "companyName", "Company already exist"));
            }
            if (StringUtils.isBlank(company.getContactNo())) {
                bindingResult.addError(new FieldError("company", "contactNo", "Contact can not be blank"));
            }
            if (company.getContactNo() != null && !company.getContactNo().matches("^[0-9].{9}+$")) {
                bindingResult.addError(new FieldError("company", "contactNo", "Contact must be of 10 digits"));
            }
            if (StringUtils.isBlank(company.getCompanyAddress())) {
                bindingResult.addError(new FieldError("company", "companyAddress", "Address can not be blank"));
            }
            if (!existingCompany.getContactNo().equalsIgnoreCase(company.getContactNo()) && findCompanyByContact(company.getContactNo()).isPresent()) {
                bindingResult.addError(new FieldError("company", "contactNo", "Contact already in use"));
            }
        }
    }

    @Override
    public void updateCompany(Company company, int id) {
        Company company1=companyRepository.findById(id).get();
        company1.setCompanyName(company.getCompanyName());
        company1.setCompanyAddress(company.getCompanyAddress());
        company1.setContactNo(company.getContactNo());
        companyRepository.save(company1);
    }

    @Override
    public void deleteCompanyById(int id) {
        companyRepository.deleteById(id);
    }
}
