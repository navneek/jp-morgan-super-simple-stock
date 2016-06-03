package com.jpm.stock.service;

import com.jpm.stock.dao.IStockRepository;
import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.domain.TradeIndicator;
import com.jpm.stock.exception.StockNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestTradeService extends AbstractSpringTest{

    @Autowired
    private ITradeService tradeService;

    @Autowired
    IStockRepository stockRepository;

    @Test
    public void testRecordTrade() throws StockNotFoundException {
        final Stock stock = stockRepository.findStockBySymbol("ALE");
        Trade trade = new Trade(TradeIndicator.BUY, stock);
        final boolean record = tradeService.record(trade);
        Assert.assertTrue(record);
        final List<Trade> tradesByStock = tradeService.findAllTradesByStock(stock);
        Assert.assertNotNull(tradesByStock);
        Assert.assertTrue(tradesByStock.size() ==1);
    }
}
