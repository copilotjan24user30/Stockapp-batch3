package com.stackroute.stockapp.service;

/* create interface to  validateUser,  saveUser
 * validateUser method to validate user by emailId and password, it returns
 * boolean
 * saveUser method to save user, it returns User object if saved
 * otherwise it will throw  an excpetion EmailIdAlreadyExistsException
 * 
 */

import com.stackroute.stockapp.exceptions.EmailIdAlreadyExistsException;
import com.stackroute.stockapp.model.User;


 

public interface UserService {
    public User saveUser(User user) throws EmailIdAlreadyExistsException;
    public User validateUser(String emailId, String password);
}