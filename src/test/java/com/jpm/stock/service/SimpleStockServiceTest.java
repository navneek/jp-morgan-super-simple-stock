package com.jpm.stock.service;

import com.jpm.stock.dao.IStockRepository;
import com.jpm.stock.domain.*;
import com.jpm.stock.exception.StockNotFoundException;
import com.jpm.stock.exception.StockServiceException;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/application-context.xml"})
public class SimpleStockServiceTest extends TestCase {

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

        final Double dividendYield = simpleStockService.calculateDividendYield(StockSymbol.ALE.name(), 0.0);
        Assert.assertNotNull(dividendYield);
    }

    @Test
    public void testCalculatePERatio() throws StockServiceException, StockNotFoundException {
        final Double peRatio = simpleStockService.calculatePERatio("ALE", 0.0);
        Assert.assertNotNull(peRatio);
    }

    @Test
    public void testRecordTrade() throws StockServiceException, StockNotFoundException {

        Stock stock = new Stock(StockType.COMMON, "ALE");

        Trade trade = new Trade(TradeIndicator.BUY, stock);
        trade.setPrice(new Double(100));
        trade.setShareQuantity(new Double(10));

        when(tradeService.record(trade)).thenReturn(true);

        //Actual
        final boolean recorded = simpleStockService.recordTrade(trade);

        //Assert
        Assert.assertTrue(recorded);
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


}
