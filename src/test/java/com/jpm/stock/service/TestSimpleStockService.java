package com.jpm.stock.service;

import com.jpm.stock.dao.IStockRepository;
import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.domain.TradeIndicator;
import com.jpm.stock.exception.StockNotFoundException;
import com.jpm.stock.exception.StockServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TestSimpleStockService extends AbstractSpringTest {

    @Autowired
    IStockRepository stockRepository;
    @Mock
    ITradeService tradeService;

    @InjectMocks
    @Resource
    private ISimpleStockService simpleStockService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDependencyInjection() {
        Assert.assertNotNull(simpleStockService);
    }

    @Test
    public void testCalculateDividendYield() throws StockServiceException, StockNotFoundException {

        double price = 10.15;

        //Type Common
        Stock ale = stockRepository.findStockBySymbol("ALE");
        final Double commonDividendYield = simpleStockService.calculateDividendYield(ale.getStockSymbol(), price);
        Assert.assertNotNull(commonDividendYield);
        Assert.assertEquals(commonDividendYield, new Double(ale.getLastDividend() / price));


        //Type Preferred
        Stock gin = stockRepository.findStockBySymbol("GIN");
        final Double prefDividendYield = simpleStockService.calculateDividendYield(gin.getStockSymbol(), price);
        Assert.assertNotNull(prefDividendYield);
        Assert.assertEquals(prefDividendYield, new Double(gin.getFixedDividend() * gin.getParValue() / price));


    }

    @Test
    public void testCalculatePERatio() throws StockServiceException, StockNotFoundException {
        double price = 10.15;

        //Type Common
        Stock ale = stockRepository.findStockBySymbol("ALE");
        final Double commonPERatio = simpleStockService.calculatePERatio(ale.getStockSymbol(), price);
        Assert.assertNotNull(commonPERatio);
        Assert.assertEquals(commonPERatio, new Double(price / ale.getLastDividend()));


        //Type Preferred
        Stock gin = stockRepository.findStockBySymbol("GIN");
        final Double prefPERatio = simpleStockService.calculatePERatio(gin.getStockSymbol(), price);
        Assert.assertNotNull(prefPERatio);
        Assert.assertEquals(prefPERatio, new Double(price / gin.getLastDividend()));
    }


    @Test
    public void testCalculateGBCEAllShareIndex() throws StockServiceException, StockNotFoundException {

        List<Stock> stocks = stockRepository.findAll();
        double price = 2;
        for (Stock stock : stocks) {
            stock.setTickerPrice(price);
            price = price * 2;
        }
        final Double index = simpleStockService.calculateAllShareIndex(stocks);

        //Assert
        Assert.assertEquals(index, new Double(8.0));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateGBCEAllShareIndexThrowsStockServiceException() {
        simpleStockService.calculateAllShareIndex(new ArrayList<Stock>());
    }


    @Test
    public void testCalculateVolumeWeightedStockPrice() throws StockNotFoundException {

        List<Trade> list = new ArrayList<>();
        Calendar instance = Calendar.getInstance();
        Stock ale = stockRepository.findStockBySymbol("ALE");
        double price = 1.4;
        double qty = 2;
        for (int i = 0; i < 25; i++) {
            Trade trade = new Trade(TradeIndicator.BUY, ale);
            instance.add(Calendar.MINUTE, -1);
            trade.setTime(instance.getTime());
            trade.setShareQuantity(qty);
            trade.setPrice(price);
            list.add(trade);
            qty = qty + 2;
            price = price + 2.3;

        }
        Mockito.when(tradeService.findAllTradesByStock(ale)).thenReturn(list);
        List<Trade> allTrades = tradeService.findAllTradesByStock(ale);
        Assert.assertNotNull(allTrades);
        Assert.assertTrue(allTrades.size() == 25);

        double volumeWeightedStockPrice = simpleStockService.calculateVolumeWeightedStockPrice(ale.getStockSymbol());
        Assert.assertNotNull(volumeWeightedStockPrice);
        Assert.assertTrue(volumeWeightedStockPrice ==  new Double(21.0).doubleValue());
    }

}
