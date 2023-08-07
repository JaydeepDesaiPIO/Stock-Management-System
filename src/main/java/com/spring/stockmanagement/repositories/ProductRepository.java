package com.spring.stockmanagement.repositories;

import com.spring.stockmanagement.entities.Company;
import com.spring.stockmanagement.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findProductByCompany(Company company);

    Optional<Product> findProductByProductName(String productName);

    Product findByProductName(String productName);
}
