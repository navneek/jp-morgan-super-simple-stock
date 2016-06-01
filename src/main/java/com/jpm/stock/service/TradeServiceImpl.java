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

    private Map<Stock, List<Trade>> trades = new TreeMap<>();

    @Override
    public boolean record(Trade trade) {
        trade.setTime(new Date());
        List<Trade> list = trades.get(trade.getStock());
        if (list == null || list.isEmpty()) {
            list = new ArrayList<>();
            trades.put(trade.getStock(), list);
        }
        return list.add(trade);
    }

    @Override
    public List<Trade> getTradesFromLast(int timeInMinutes) {
        List<Trade> list = new ArrayList<>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -(timeInMinutes * 60));
        Date from = calendar.getTime();
        final Collection<List<Trade>> values = trades.values();
        for (List<Trade> value : values) {
            for (Trade trade : value) {
                if (trade.getTime().after(from) && trade.getTime().before(now)) {
                    list.add(trade);
                }
            }
        }
        return list;
    }
}
