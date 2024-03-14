package com.stackroute.stockapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/*
 * Use Mockito ,MockBean,InjectMock
 * create  StockListingRepository mock
 * create  RestTemplate mock
 * create  StockListingService service
 * create  StockListingServiceImplTest
 * write test case for all the methods available in the StockListingServiceImpl
 * 
 * 
 */

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.stackroute.stockapp.exceptions.StockAlreadyExistsException;
import com.stackroute.stockapp.exceptions.StockNotFoundException;
import com.stackroute.stockapp.model.Stock;
import com.stackroute.stockapp.model.StockList;
import com.stackroute.stockapp.repository.StockListingRepository;   

@ExtendWith(MockitoExtension.class)
class StockListingServiceImplTest {

    @Mock
    private StockListingRepository stockListingRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StockListingServiceImpl stockListingService;

    private Stock stock1;
    private Stock stock2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stock1 =new Stock("AAPL", "Apple Inc", "USD", "NASDAQ", "XNGS", "United States", "Common Stock");   
        stock2 = new Stock("MSFT", "Microsoft Corporation", "USD", "NASDAQ", "XNGS", "United States", "Common Stock"); 

     }
     //tearDown method
    @AfterEach
    public void tearDown() {
        stock1 = null;
        stock2 = null;
    }

    @Test
    public void testSaveStock() throws StockAlreadyExistsException {
        when(stockListingRepository.existsById(stock1.getSymbol())).thenReturn(false);
        when(stockListingRepository.save(stock1)).thenReturn(stock1);
        Stock savedStock = stockListingService.saveStock(stock1);
        assertEquals(stock1, savedStock);
        verify(stockListingRepository, times(1)).existsById(stock1.getSymbol());
        verify(stockListingRepository, times(1)).save(stock1);
    }

    @Test
    public void testSaveStockStockAlreadyExistsException() {
        when(stockListingRepository.existsById(stock1.getSymbol())).thenReturn(true);
        assertThrows(StockAlreadyExistsException.class, () -> stockListingService.saveStock(stock1));
        verify(stockListingRepository, times(1)).existsById(stock1.getSymbol());
    }

    @Test
    public void testSaveStockIllegalArgumentException() {
        stock1.setSymbol(null);
        assertThrows(IllegalArgumentException.class, () -> stockListingService.saveStock(stock1));
    }

    @Test
    public void testGetAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        when(stockListingRepository.findAll()).thenReturn(stocks);
        List<Stock> allStocks = stockListingService.getAllStocks();
        assertEquals(stocks, allStocks);
        verify(stockListingRepository, times(1)).findAll();
    }

    @Test
    public void testGetStockBySymbol() throws StockNotFoundException {
        when(stockListingRepository.findById(stock1.getSymbol())).thenReturn(java.util.Optional.ofNullable(stock1));
        Stock stockBySymbol = stockListingService.getStockById(stock1.getSymbol());
        assertEquals(stock1, stockBySymbol);
        verify(stockListingRepository, times(1)).findById(stock1.getSymbol());
    }

    @Test
    public void testGetStockBySymbolStockNotFoundException() {
        when(stockListingRepository.findById(stock1.getSymbol())).thenReturn(Optional.empty());
        assertThrows(StockNotFoundException.class, () -> stockListingService.getStockById(stock1.getSymbol()));
        verify(stockListingRepository, times(1)).findById(stock1.getSymbol());
    }

    @Test
    public void testDeleteStock() throws StockNotFoundException {
        when(stockListingRepository.existsById(stock1.getSymbol())).thenReturn(true);
        doNothing().when(stockListingRepository).deleteById(stock1.getSymbol());
        boolean deleted = stockListingService.deleteStock(stock1.getSymbol());
        assertTrue(deleted);
        verify(stockListingRepository, times(1)).existsById(stock1.getSymbol());
        verify(stockListingRepository, times(1)).deleteById(stock1.getSymbol());
    }

    @Test

    public void testDeleteStockStockNotFoundException() {
        when(stockListingRepository.existsById(stock1.getSymbol())).thenReturn(false);
        assertThrows(StockNotFoundException.class, () -> stockListingService.deleteStock(stock1.getSymbol()));
        verify(stockListingRepository, times(1)).existsById(stock1.getSymbol());
    }
   //getStocksByCountryFromAPI(String country) 
    @Test
    public void testGetStocksByCountryFromAPI() {
        String country = "United States";
        StockList stockList = new StockList();
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        stockList.setData(stocks);
               
        when(restTemplate.getForObject("https://api.twelvedata.com/stocks?country=" +country
                        , StockList.class)).thenReturn(stockList);
        stockListingService.setAPI_URL("https://api.twelvedata.com/stocks");
         StockList stocksByCountry = stockListingService.getStocksByCountryFromAPI(country);
        assertEquals(2, stocksByCountry.getData().size());
        verify(restTemplate, times(1)).getForObject("https://api.twelvedata.com/stocks?country=" +country, StockList.class);
    }

    //getStocksByCurrency(String currency)

    @Test
    public void testGetStocksByCurrency() {
        String currency = "USD";
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);
        when(stockListingRepository.findByCurrency(currency)).thenReturn(stocks);
        List<Stock> stocksByCurrency = stockListingService.getStocksByCurrency(currency);
        assertEquals(stocks, stocksByCurrency);
        verify(stockListingRepository, times(1)).findByCurrency(currency);
    }

 

    
}


  