package com.jpm.stock.service;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;

import java.util.List;

/**
 * Trade service provides functions to record {@link com.jpm.stock.domain.Trade}
 */
public interface ITradeService {

    /**
     * Records a trade for the given {@link com.jpm.stock.domain.Trade}
     *
     * @param trade
     * @return
     */
    boolean record(Trade trade);

    /**
     * Return all trades by given Stock
     *
     * @param stock
     * @return
     */
    List<Trade> findAllTradesByStock(Stock stock);

    /**
     * Delete all trades by given stock
     *
     * @param stock
     */
    void deleteTrades(Stock stock);
}
