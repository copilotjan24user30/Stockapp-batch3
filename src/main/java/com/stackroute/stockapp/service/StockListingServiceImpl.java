package com.stackroute.stockapp.service;

 /* implements all the methods in the StockListingService
  * autowire  StockListingRepository ,RestTemplate 
  create API_URL attribute to get the value from application.properties  API_BASE_URL
  
  */
import java.util.List;
import java.util.Optional;

import com.stackroute.stockapp.exceptions.StockAlreadyExistsException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.model.StockList;
import com.stackroute.stockapp.repository.StockListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

/**
 * Service class that implements all the methods in the StockListingService.
 * It uses the StockListingRepository to interact with the database and RestTemplate to interact with an external API.
 */
@Service
public class StockListingServiceImpl implements StockListingService {

    @Autowired
    private StockListingRepository stockListingRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${API_BASE_URL}")
    private String API_URL;

    public void  setAPI_URL(String API_URL){
        this.API_URL = API_URL;
    }

 /**
     * Saves a new stock to the database.
     * @param stock The stock to save.
     * @return The saved stock.
     * @throws StockAlreadyExistsException If the stock already exists in the database.
     * @throws IllegalArgumentException If the stock or stock symbol is null or empty.
     */
    @Override
    public Stock saveStock(Stock stock) throws StockAlreadyExistsException {
        if (stock == null || stock.getSymbol() == null || stock.getSymbol().isEmpty()) {
            throw new IllegalArgumentException("Stock or stock symbol cannot be null or empty");
        }
       
        if (stockListingRepository.existsById(stock.getSymbol())) {
            throw new StockAlreadyExistsException("Stock already exists");
        }
        return stockListingRepository.save(stock);
    }

    
    /**
     * Deletes a stock from the database.
     * @param id The ID of the stock to delete.
     * @return true if the stock was deleted successfully.
     * @throws StockNotFoundException If the stock does not exist in the database.
     */

    @Override
    public boolean deleteStock(String id) throws StockNotFoundException {
        if (!stockListingRepository.existsById(id)) {
            throw new StockNotFoundException("Stock not found");
        }
        stockListingRepository.deleteById(id);
        return true;
    }

    
    /**
     * Retrieves a stock from the database by its ID.
     * @param id The ID of the stock to retrieve.
     * @return The retrieved stock.
     * @throws StockNotFoundException If the stock does not exist in the database.
     */
    @Override
    public Stock getStockById(String id) throws StockNotFoundException {
        //use findById method of stockListingRepository to get the stock by id , if  found
         // return the stock object, not found throw StockNotFoundException   use Optional to handle null

        //  Optional<Stock> stock = stockListingRepository.findById(id);
        //     if(stock.isPresent()){
        //         return stock.get();
        //     }
        //     else{
        //         throw new StockNotFoundException("Stock not found");
        //     }


        // use orElseThrow method of Optional to handle null

        return stockListingRepository.findById(id).orElseThrow(() -> new StockNotFoundException("Stock not found"));
        // return stockListingRepository.findById(id).orElseThrow(() -> new StockNotFoundException("Stock not found"));

       
         
    }

    /**
     * Updates an existing stock in the database.
     * @param stock The stock to update. The stock's symbol is used to find the existing stock.
     * @return The updated stock.
     * @throws StockNotFoundException If the stock does not exist in the database.
     */
    @Override
    public Stock updateStock(Stock stock) throws StockNotFoundException {
        if (!stockListingRepository.existsById(stock.getSymbol())) {
            throw new StockNotFoundException("Stock not found");
        }
        return stockListingRepository.save(stock);
    }

    /**
     * Retrieves all stocks from the database.
     * @return A list of all stocks.
     */
    @Override
    public List<Stock> getAllStocks() {
        return stockListingRepository.findAll();
    }

    @Override
    public List<Stock> getStocksByCurrency(String currency) {
        return stockListingRepository.findByCurrency(currency);
    }

    @Override
    public StockList getStocksByCountryFromAPI(String country) {

        //use RestTemplate to get the stocks from the API based on the country
        // use getForObject method of RestTemplate to get the stocks from the API based on the country
        // use API_URL to get the value from application.properties  API_BASE_URL
        // return the list of stocks
        String url = API_URL + "?country=" + country;
        StockList stockList = restTemplate.getForObject(url, StockList.class);
        return stockList;

        
    }
       
}
