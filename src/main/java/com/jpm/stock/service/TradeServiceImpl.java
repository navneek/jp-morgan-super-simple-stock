package com.jpm.stock.service;

import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Simple implementation of {@link com.jpm.stock.service.ITradeService}
 */
@Service
public class TradeServiceImpl implements ITradeService {

    private Map<Stock, List<Trade>> trades = new HashMap<>();

    @Override
    public boolean record(Trade trade) {
        List<Trade> list = trades.get(trade.getStock());
        if (list == null || list.isEmpty()) {
            list = new ArrayList<>();
            trades.put(trade.getStock(), list);
        }
        return list.add(trade);
    }

    @Override
    public List<Trade> findAllTradesByStock(Stock stock) {
        return Collections.unmodifiableList(trades.get(stock));
    }

    @Override
    public void deleteTrades(Stock stock) {
        trades.remove(stock);
    }
}
