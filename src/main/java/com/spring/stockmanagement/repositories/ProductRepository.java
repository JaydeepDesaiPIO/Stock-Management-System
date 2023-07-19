package com.spring.stockmanagement.repositories;

import com.spring.stockmanagement.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

  //  @Query("select u from product u where u.product_name = :productName")
    public Product getProductByProductName(String productName);

    public Optional<Product> findByProductName(String productName);
}
