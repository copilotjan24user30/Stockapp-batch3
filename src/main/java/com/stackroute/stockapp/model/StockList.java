package com.stackroute.stockapp.model;

/*  create   data is of type List of stock 
 * Use Lombok data annotation to  generate getter and setter for all the attributes
 * Use lombok to generate NoArgsConstructor and AllArgsConstructor
 * 
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockList {
    private List<Stock> data;
}