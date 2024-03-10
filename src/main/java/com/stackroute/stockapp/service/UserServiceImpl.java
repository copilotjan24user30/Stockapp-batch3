package com.stackroute.stockapp.service;

/*  Autowire UserRepository
 * create me  method to save user, validateUser
 * validateUser method to validate user by emailId and password
 * 
 * 
 */

import com.stackroute.stockapp.model.User;
import com.stackroute.stockapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.stackroute.stockapp.exceptions.EmailIdAlreadyExistsException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) throws EmailIdAlreadyExistsException {
        //check if the user already exists  by emailId
        Optional<User> userExists = userRepository.findById(user.getEmailId());
        if (userExists.isPresent()) {
            throw new EmailIdAlreadyExistsException("User with emailId already exists");
        }
       return userRepository.save(user);
    }

    @Override
    public User validateUser(String emailId, String password) {
        Optional<User> user = userRepository.findByEmailIdAndPassword(emailId, password);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }
}




 