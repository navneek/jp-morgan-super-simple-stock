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
    public void testCalculateDividendYield() throws StockServiceException, StockNotFoundException {

        //Input Price
        double price = 10.15;

        //Test Stock of type Common
        Stock ale = stockRepository.findStockBySymbol("ALE");
        final Double dividendYield = simpleStockService.calculateDividendYield(ale.getStockSymbol(), price);
        Assert.assertNotNull(dividendYield);
        Assert.assertEquals(dividendYield, new Double(ale.getLastDividend() / price));


        //Test stock of type Preferred
        Stock gin = stockRepository.findStockBySymbol("GIN");
        final Double calculatedDividendYield = simpleStockService.calculateDividendYield(gin.getStockSymbol(), price);
        Assert.assertNotNull(calculatedDividendYield);
        Assert.assertEquals(calculatedDividendYield, new Double(gin.getFixedDividend() * gin.getParValue() / price));


    }

    @Test
    public void testCalculatePERatio() throws StockServiceException, StockNotFoundException {

        //Given Stocks
        Stock ale = stockRepository.findStockBySymbol("ALE");
        Stock gin = stockRepository.findStockBySymbol("GIN");

        //Given Price
        double givenPrice = 10.15;

        //Test with Stock of type Common
        final Double commonPERatio = simpleStockService.calculatePERatio(ale.getStockSymbol(), givenPrice);
        Assert.assertNotNull(commonPERatio);
        Assert.assertEquals(commonPERatio, new Double(givenPrice / ale.getLastDividend()));


        //Test with Stock of type Preferred
        final Double prefPERatio = simpleStockService.calculatePERatio(gin.getStockSymbol(), givenPrice);
        Assert.assertNotNull(prefPERatio);
        Assert.assertEquals(prefPERatio, new Double(givenPrice / gin.getLastDividend()));
    }


    @Test
    public void testCalculateGBCEAllShareIndex() throws StockServiceException, StockNotFoundException {

        //Prepare input of Stocks with price
        List<Stock> stocks = stockRepository.findAll();
        double price = 2;
        for (Stock stock : stocks) {
            stock.setTickerPrice(price);
            price = price * 2;
        }

        //Calculate the GBCE
        final Double index = simpleStockService.calculateAllShareIndex(stocks);

        //Assert
        Assert.assertEquals(index, new Double(8.0));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateGBCEAllShareIndexThrowsStockServiceException() {

        //The service should throw exception when passing empty stock list
        simpleStockService.calculateAllShareIndex(new ArrayList<Stock>());
    }


    @Test
    public void testCalculateVolumeWeightedStockPrice() throws StockNotFoundException {

        //Prepare a Stock and Trades
        Stock stock = stockRepository.findStockBySymbol("ALE");
        List<Trade> list = createTrades(stock);

        //Set Expectation
        Mockito.when(tradeService.findAllTradesByStock(stock)).thenReturn(list);

        //Test
        double volumeWeightedStockPrice = simpleStockService.calculateVolumeWeightedStockPrice(stock.getStockSymbol());
        Assert.assertNotNull(volumeWeightedStockPrice);
        Assert.assertTrue(volumeWeightedStockPrice ==  new Double(21.0).doubleValue());
    }

    private List<Trade> createTrades(Stock stock) {
        List<Trade> list = new ArrayList<>();
        Calendar instance = Calendar.getInstance();
        double price = 1.4;
        double qty = 2;
        for (int i = 0; i < 25; i++) {
            Trade trade = new Trade(TradeIndicator.BUY, stock);
            instance.add(Calendar.MINUTE, -1);
            trade.setTime(instance.getTime());
            trade.setShareQuantity(qty);
            trade.setPrice(price);
            list.add(trade);
            qty = qty + 2;
            price = price + 2.3;
        }
        return list;
    }

}
