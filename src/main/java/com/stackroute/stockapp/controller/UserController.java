package com.stackroute.stockapp.controller;

/*  create a Rest Controller.Autowire  UserServiceImpl
 * create a method to save user, validateUser
 * provide the mapping  /register to   saveUser  ,/login to validateUser 
 * use /api/v1/user as base path
 * 
 */

import com.stackroute.stockapp.exceptions.EmailIdAlreadyExistsException;
import com.stackroute.stockapp.model.User;
import com.stackroute.stockapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user) throws EmailIdAlreadyExistsException {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> validateUser(@RequestBody User user) {
        //if the user is null then return 404
        ResponseEntity<?> responseEntity;

        User userExists = userService.validateUser(user.getEmailId(), user.getPassword());
        if (userExists != null) {
            String token = generateToken(user.getEmailId());
            responseEntity= new ResponseEntity<>(token, HttpStatus.OK);
        }
        else {
            responseEntity= new ResponseEntity<>("Invalid Credentials", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    //create method generateToken to generate a token
    //take emailId as argument , using Jwts.builder generate a token

    private String generateToken(String emailId) {
        return Jwts.builder().setSubject(emailId).setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(SignatureAlgorithm.HS256, "CTS-BATCH3-SUCCESS").compact();
    }
 
 
         
    }


 


