package com.stackroute.stockapp.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

  /*
  * create  DataMongoTest  for testing
  *  As a part  of setup delete all the records from the database
    *  write test case to save stock
    *  write test case to delete stock
    *  write test case to get all stocks
    *  write test case to get stock by symbol
    *  write test case to get stock by country
    *  write test case to get stock by exchange and country
    create  two stock objects and save them in the database
    autowire  StockRepository
  */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
 

import com.stackroute.stockapp.model.Stock;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;







@DataMongoTest
public class StockListingRepositoryTest {

    @Autowired
    private StockListingRepository repository;

    private  Stock  stock1;
    private Stock  stock2;

    //create  setup method to delete all the records from the database
    @BeforeEach
    public void setUp() {
         stock1 =new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");   
        stock2 = new Stock("MSFT", "Microsoft Corporation", "USD", "NASDAQ", "XNGS", "United States", "Common Stock"); 
        repository.deleteAll();
    }
    //write teardown method
    @AfterEach
    public void tearDown() {
        stock1 = null;
        stock2 = null;
    
    }

     @Test
    public void testFindByCurrency() {
        repository.save(stock1);
        repository.save(stock2);
       List<Stock> result = repository.findByCurrency("USD");
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCurrency()).isEqualTo("USD");
    }

    @Test
    public void testFindByExchangeAndCountry() {
        // Given
        repository.save(stock1);
        repository.save(stock2);
         // When
        List<Stock> result = repository.findByExchangeAndCountry("NASDAQ", "United States");
        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getExchange()).isEqualTo("NASDAQ");
        assertThat(result.get(0).getCountry()).isEqualTo("United States");
    }

    //create  negative test case for findByCurrency
    @Test
    public void testFindByCurrencyNegative() {
        repository.save(stock1);
        repository.save(stock2);
        List<Stock> result = repository.findByCurrency("INR");
        assertThat(result).isEmpty();
    }

    //create  negative test case for findByExchangeAndCountry
    @Test
    public void testFindByExchangeAndCountryNegative() {
        repository.save(stock1);
        repository.save(stock2);
        List<Stock> result = repository.findByExchangeAndCountry("NYSE", "United States");
        assertThat(result).isEmpty();
    }

    //create  test case to save stock
    @Test
    public void testSaveStock() {
        Stock savedStock = repository.save(stock1);
        assertThat(savedStock).isNotNull();
        assertThat(savedStock.getSymbol()).isEqualTo(stock1.getSymbol());
    }

    //create test case to delete stock
    @Test
    public void testDeleteStock() {
        repository.save(stock1);
        repository.save(stock2);
        repository.deleteById(stock1.getSymbol());
        assertThat(repository.existsById(stock1.getSymbol())).isFalse();
    }

    //create test case to get all stocks
    @Test
    public void testGetAllStocks() {
        repository.save(stock1);
        repository.save(stock2);
        List<Stock> result = repository.findAll();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
    }

    //create test case to  exist by symbol
    @Test
    public void testExistsBySymbol() {
        repository.save(stock1);
        repository.save(stock2);
        assertThat(repository.existsById(stock1.getSymbol())).isTrue();
    }
    
}

 