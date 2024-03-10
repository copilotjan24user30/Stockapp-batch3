package com.stackroute.stockapp.repository;

import com.stackroute.stockapp.model.User;

import java.util.OptionalInt;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;




@Repository
public interface UserRepository extends MongoRepository<User,String>   {
  // method to find user by emailId and password returns Optional of User
    public Optional<User> findByEmailIdAndPassword(String emailId, String password);
}
