package com.spring.stockmanagement.service;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.repositories.CompanyRepository;
import com.spring.stockmanagement.service.Interface.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }
}
