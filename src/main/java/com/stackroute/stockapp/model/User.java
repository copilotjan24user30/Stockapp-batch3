package com.stackroute.stockapp.model;
/* create the following attributes 
 * emailId , password,username,mobileNumber
 * Use Lombok  @Data to provide getter and setter for all the attributes
 * toString
 * Use @Document annotation to specify the name of the collection in which the object will be stored
 * annotate @id for emailId
 * Use @NoArgsConstructor and @AllArgsConstructor to generate No argument constructor and All argument constructor
 * 
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String emailId;
    private String password;
    private String username;
    private String mobileNumber;
}


