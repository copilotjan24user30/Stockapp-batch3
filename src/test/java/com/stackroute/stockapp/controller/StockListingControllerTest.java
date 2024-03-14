package com.stackroute.stockapp.controller;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.stockapp.exceptions.GlobalExceptionHandler;
import com.stackroute.stockapp.exceptions.StockAlreadyExistsException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.model.StockList;
import com.stackroute.stockapp.service.StockListingService;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.stackroute.stockapp.exceptions.StockAlreadyExistsException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.service.StockListingService;

/*
 *  use @WebMvcTest annotation to test the controller
 * use @Autowired to inject the MockMvc
 * use @MockBean to inject the StockListingService
 * create test cases to test all the end points of the StockListingController
 *  
 */

 

@WebMvcTest(StockListingController.class)
public class StockListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockListingService stockListingService;

    @InjectMocks
    private StockListingController stockListingController;
    private Stock stock1;
    private Stock stock2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stock1 = new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
        stock2 = new Stock("GOOGL", "Alphabet Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");
        mockMvc = MockMvcBuilders.standaloneSetup(stockListingController).setControllerAdvice(new GlobalExceptionHandler()).build();

    }

    @Test
    public void saveStockTest() throws Exception {
       when(stockListingService.saveStock(stock1)).thenReturn(stock1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(stock1)))
              //  .content("{\"id\":\"1\",\"name\":\"Apple Inc.\",\"currency\":\"USD\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value("AAPL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Apple Inc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("USD"));
    }

    @Test
    public void deleteStockTest() throws Exception {
        String stockId = "AAPL";
        when(stockListingService.deleteStock(stockId)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/stock/{id}", stockId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content() .string("Stock Deleted with Id "+stockId +" successfully"));
    }

    @Test
    public void getStockByIdTest() throws Exception {
        String stockId="AAPL";
         when(stockListingService.getStockById(stockId)).thenReturn(stock1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stock/{id}", stockId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbol").value(stockId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Apple Inc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("USD"));
    }

    @Test
    public void getAllStocksTest() throws Exception {
        when(stockListingService.getAllStocks()).thenReturn(java.util.Arrays.asList(stock1, stock2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stock"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Apple Inc"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency").value("USD"));

    }

    @Test
    public void getStocksByCurrencyTest() throws Exception {
        String currency = "USD";
        when(stockListingService.getStocksByCurrency(currency)).thenReturn(java.util.Arrays.asList(stock1, stock2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stock/currency/{currency}", currency))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getStocksByCountryFromAPITest() throws Exception {
        String country = "USA";
List<Stock>  list = java.util.Arrays.asList(stock1, stock2);
StockList stockList = new StockList(list);


        when(stockListingService.getStocksByCountryFromAPI(country)).thenReturn(stockList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/stock/country/{country}", country))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}


 
 


 
