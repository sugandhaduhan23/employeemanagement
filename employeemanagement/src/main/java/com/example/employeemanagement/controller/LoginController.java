package com.example.employeemanagement.controller;

import java.util.HashMap;
import java.util.logging.Logger;

import com.example.employeemanagement.dto.MessageDetails;
import com.example.employeemanagement.dto.UserInfo;
import com.example.employeemanagement.exceptions.EmployeeNotFoundException;
import com.example.employeemanagement.exceptions.InvalidCredentialException;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/login")
public class LoginController {
   
    private static Logger logger = Logger.getLogger(LoginController.class.getName());
    @Autowired
    private UserRepository userRepository;

    /*************************************************************
     * This API will fetch all the user credentials of all
     * the employees.
    **************************************************************/
    @GetMapping
    public Iterable<User> getUsers() {
        logger.info("Fetching Users");   
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public User getUsersById(@PathVariable Integer userId) {
        logger.info("Fetching User By Id");   
        return userRepository.findById(userId).get();
    }


    /*************************************************************
     * This API will verify the user credentials in the database 
     * and allow the use to login
     **************************************************************/
    @PostMapping
    public ResponseEntity<HashMap<String, Object>> authenticateUser(@RequestBody User user) {
        logger.info("Verifying user credentials");
        user = userRepository.verifyUserCredentials(user.getEmail(), user.getPassword());
        if(user == null)
            throw new InvalidCredentialException();
        MessageDetails msg = new MessageDetails("User logged in successfully");
        HashMap<String, Object> map =  new HashMap<String, Object>();
        map.put("message", msg.getMessage());
        map.put("data", new UserInfo(user.getUserId(), user.getEmployee(), user.getEmail(), user.isFirstTimeLogin()));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
    }


    /*************************************************************
     * This API will update the user password
     **************************************************************/
    @PutMapping
    public ResponseEntity<MessageDetails> updatePassword(@RequestBody User user) {
        logger.info("Update User Password");
        User usr;
        if(user.getUserId() == null){
            usr = userRepository.findByEmail(user.getEmail());
            throw new EmployeeNotFoundException();
        }
        else{
            usr = userRepository.findById(user.getUserId()).get();
        }
        usr.setFirstTimeLogin(false);
        usr.setPassword(user.getPassword());
        userRepository.save(usr);
        MessageDetails msg = new MessageDetails("User logged in successfully");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }
    
}
