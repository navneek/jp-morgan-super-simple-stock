package com.jpm.stock.service;

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
     * Return Trade recorded in last given in minutes
     *
     * @param time
     * @return
     */
    List<Trade> getTradesFromLast(int time);
}
