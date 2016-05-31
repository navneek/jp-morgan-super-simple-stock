package com.jpm.stock.dao;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.exception.StockNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple implementation of Stock Repository
 */
@Repository
public class StockRepositoryImpl implements IStockRepository {

    private static Map<String, Stock> db= new HashMap<>();

    static {
        db.put("TEA", new Stock("TEA", StockType.COMMON, 0.0, 0.0, 100.0));
        db.put("POP", new Stock("POP", StockType.COMMON, 8.0, 0.0, 100.0));
        db.put("ALE", new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0));
        db.put("GIN", new Stock("GIN", StockType.PREFERRED, 8.0, 0.2, 100.0));
        db.put("JOE", new Stock("JOE", StockType.COMMON, 13.0, 0.0, 250.0));
    }

    @Override
    public Stock findStockBySymbol(String symbol) throws StockNotFoundException {

        if (symbol == null || symbol.isEmpty()) {
            throw new StockNotFoundException("Could not find a stock for the given symbol " + symbol);
        }
        return null;
    }

    @Override
    public List<Stock> findAll() throws StockNotFoundException {
        return null;
    }
}
