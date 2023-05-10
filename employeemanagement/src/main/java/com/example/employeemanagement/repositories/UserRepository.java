package com.example.employeemanagement.repositories;

import com.example.employeemanagement.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    
    @Query(value="SELECT * FROM user WHERE email = :email and password = :password ", nativeQuery = true)
    User verifyUserCredentials(String email, String password);

    User findByEmail(String email);
}
