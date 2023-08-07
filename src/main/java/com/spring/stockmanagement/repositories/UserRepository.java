package com.spring.stockmanagement.repositories;

import com.spring.stockmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //    @Query("select u from User u where u.name = :name")
    public Optional<User> findByName(String name);

    public Optional<User> findByEmail(String email);

    public Optional<User> findByContact(String contact);

    @Query("select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    public List<User> getUserByCompanyId(int id);
}
