package com.spring.stockmanagement.repositories;

import com.spring.stockmanagement.entities.MyCart;
import com.spring.stockmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyCartRepository extends JpaRepository<MyCart,Integer> {

    List<MyCart> getCartByUser(User user);
}
