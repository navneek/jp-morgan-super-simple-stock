package com.jpm.stock.service;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.exception.StockNotFoundException;
import com.jpm.stock.exception.StockServiceException;

import java.util.List;

/**
 * Simple Stock Service
 */
public interface ISimpleStockService {


    /**
     * Calculate the dividend yield for the given Stock and price
     *
     * @param stockSymbol
     * @param price
     * @return
     * @throws StockServiceException
     */
    Double calculateDividendYield(String stockSymbol, double price) throws StockServiceException, StockNotFoundException;

    /**
     * Calculates the PE ration for the given Stock and price
     *
     * @param stockSymbol
     * @param price
     * @return
     * @throws StockServiceException
     */
    Double calculatePERatio(String stockSymbol, double price) throws StockServiceException, StockNotFoundException;

    /**
     * Records a trade
     *
     * @param trade
     * @return
     * @throws StockServiceException
     */
    boolean recordTrade(Trade trade) throws StockServiceException;

    /**
     * Calculate the Stock price for the given Stock from the trades recorded in given minutes
     *
     * @return the volume weighted stock price
     * @throws StockServiceException
     */
    Double calculateVolumeWeightedStockPrice() throws StockServiceException;


    /**
     * Calculates the All Share Index using the geometric mean of prices for all stocks
     * @param stocks
     * @return
     * @throws StockServiceException
     */
    double calculateAllShareIndex(List<Stock> stocks) throws StockServiceException;
}
