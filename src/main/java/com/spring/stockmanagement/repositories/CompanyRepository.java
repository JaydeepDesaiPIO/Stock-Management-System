package com.spring.stockmanagement.repositories;

import com.spring.stockmanagement.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

    public Optional<Company> findByCompanyName(String name);
}
