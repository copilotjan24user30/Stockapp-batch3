package com.stackroute.stockapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.stockapp.model.Stock;


@Repository
public interface StockListingRepository extends MongoRepository<Stock, String>{

    // create a method to get  all the stock details by currency
     public  List<Stock> findByCurrency(String currency);

     // create a method to get  all the stock details by exchange and country

        public  List<Stock> findByExchangeAndCountry(String exchange, String country);

 


}
