package com.stackroute.stockapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.stockapp.exceptions.StockAlreadyExistsException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.service.StockListingService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;



 

/*
 * Use @Autowired to inject the StockListingService
    create methods to save, delete, get stock by id, get all stocks
    update stock, get all stocks by currency, get all stocks by country from API
     use appropriate annotations to handle the exceptions ,don't use try catch block
     use /api/v1/stock as the base URI
 */
@RestController
@RequestMapping("/api/v1/stock")
@CrossOrigin
public class StockListingController {

    @Autowired
    private StockListingService stockListingService;


    @PostMapping
    public ResponseEntity<Stock> saveStock(@RequestBody Stock stock) throws StockAlreadyExistsException {
        return new ResponseEntity<>(stockListingService.saveStock(stock), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(@PathVariable String id) throws StockNotFoundException {
        boolean status = stockListingService.deleteStock(id);
        return new ResponseEntity<>("Stock Deleted with Id "+id +" successfully", HttpStatus.OK);
    }

     @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable String id) throws StockNotFoundException {
         return new ResponseEntity<>(stockListingService.getStockById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllStocks() {
        return new ResponseEntity<>(stockListingService.getAllStocks(), HttpStatus.OK);
    }

    @GetMapping("/currency/{currency}")
    public ResponseEntity<?> getStocksByCurrency(@PathVariable String currency) {
        return new ResponseEntity<>(stockListingService.getStocksByCurrency(currency), HttpStatus.OK);  
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<?> getStocksByCountryFromAPI(@PathVariable String country) {
        return new ResponseEntity<>(stockListingService.getStocksByCountryFromAPI(country), HttpStatus.OK);

    }


}




 

  

 