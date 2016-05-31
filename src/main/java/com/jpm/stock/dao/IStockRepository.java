package com.jpm.stock.dao;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.exception.StockNotFoundException;

import java.util.List;

/**
 * Stock repository
 */
public interface IStockRepository {

    Stock findStockBySymbol(String symbol) throws StockNotFoundException;

    List<Stock> findAll() throws StockNotFoundException;
}
