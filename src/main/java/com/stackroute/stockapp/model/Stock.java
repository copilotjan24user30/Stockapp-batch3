package com.stackroute.stockapp.model;

 /* create a class with following attributes
  * {
    "symbol": "AAPL",
    "name": "Apple Inc",
    "currency": "USD",
    "exchange": "NASDAQ",
    "mic_code": "XNGS",
    "country": "United States",
    "type": "Common Stock"
  }
  Use Lombok to  generate getter and setter for all the attributes
  Use Lombok @Data to generate the NoArgsConstructor and AllArgsConstructor
  toString method
  use  @Document annotation to specify the name of the collection in which the object will be stored
  annotate @id for symbol

  */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stock")
public class Stock {
    @Id
      private String symbol;
      private String name;
      private String currency;
      private String exchange;
      private String mic_code;
      private String country;
      private String type;
    
   // private String email;

    
}

 

 
