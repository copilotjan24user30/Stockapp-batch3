package com.stackroute.stockapp.service;

 /* creating interfacw with the method to  save stock ,delete stock,get all the stocks,
  * update stock, get all the stocks by currency,  
    To get all the stocks from the  API based on the country
     methods  may throw  appropriate exceptions like
      StockNotFoundException, StockAlreadyExistsException
      
  */

import java.util.List;

import com.stackroute.stockapp.exceptions.StockAlreadyExistsException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.model.StockList;

public interface StockListingService {
    public Stock saveStock(Stock stock) throws StockAlreadyExistsException;
    public boolean deleteStock(String id) throws StockNotFoundException;
    public Stock getStockById(String id) throws StockNotFoundException;
    public Stock updateStock(Stock stock) throws StockNotFoundException;
    public List<Stock> getAllStocks();
    public List<Stock> getStocksByCurrency(String currency);
    public StockList getStocksByCountryFromAPI(String country);
}

 
 
 