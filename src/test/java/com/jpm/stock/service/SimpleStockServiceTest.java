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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/application-context.xml"})
public class SimpleStockServiceTest extends TestCase {

    @Mock IStockRepository stockRepository;
    @Mock ITradeService tradeService;

    @InjectMocks
    @Resource
    private ISimpleStockService simpleStockService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDependencyInjection(){
        Assert.assertNotNull(simpleStockService);
    }

    @Test
    public void testCalculateDividendYield() throws StockServiceException, StockNotFoundException {

        Stock stock = new Stock(StockType.COMMON, StockSymbol.ALE);

        when(stockRepository.findStockBySymbol(anyString())).thenReturn(stock);

        final Double dividendYield = simpleStockService.calculateDividendYield(StockSymbol.ALE.name(), 0.0);
        Assert.assertNotNull(dividendYield);
    }

    @Test(expected = StockServiceException.class)
    public void testCalculateDividendYieldThrowsStockServiceException() throws StockServiceException, StockNotFoundException {

        when(stockRepository.findStockBySymbol("")).thenThrow(StockNotFoundException.class);

        simpleStockService.calculateDividendYield("", 0.0);
    }

    @Test
    public void testCalculatePERatio() throws StockServiceException, StockNotFoundException {

        Stock stock = new Stock(StockType.COMMON, StockSymbol.ALE);

        when(stockRepository.findStockBySymbol(anyString())).thenReturn(stock);

        final Double peRatio = simpleStockService.calculatePERatio(StockSymbol.ALE.name(), 0.0);
        Assert.assertNotNull(peRatio);
    }

    @Test(expected = StockServiceException.class)
    public void testCalculatePERatioThrowsStockServiceException() throws StockServiceException, StockNotFoundException {

        when(stockRepository.findStockBySymbol("")).thenThrow(StockNotFoundException.class);

        simpleStockService.calculatePERatio("", 0.0);
    }

    @Test
    public void testRecordTrade() throws StockServiceException, StockNotFoundException {

        Stock stock = new Stock(StockType.COMMON, StockSymbol.ALE);
        Trade trade = new Trade(TradeIndicator.BUY, stock);

        trade.setPrice(new Double(100));
        trade.setShareQuantity(new Double(10));
        trade.setTime(new Date());

        when(stockRepository.findStockBySymbol(anyString())).thenReturn(stock);
        when(tradeService.record(trade)).thenReturn(trade);

        //Actual
        final Trade recorded = simpleStockService.recordTrade(trade);

        //Assert
        Assert.assertNotNull(recorded);
        Assert.assertEquals(trade, recorded);
    }

    @Test
    public void testCalculateGBCEAllShareIndex() throws StockServiceException, StockNotFoundException {

        List<Stock> all = new ArrayList<>();
        all.add(new Stock(StockType.COMMON, StockSymbol.ALE));
        all.add(new Stock(StockType.PREFERRED, StockSymbol.GIN));
        all.add(new Stock(StockType.COMMON, StockSymbol.JOE));

        when(stockRepository.findAll()).thenReturn(all);
        final Double index = simpleStockService.calculateGBCEAllShareIndex();

        //Assert
        Assert.assertNotNull(index);

    }

    @Test(expected = StockServiceException.class)
    public void testCalculateGBCEAllShareIndexStockServiceException() throws StockServiceException, StockNotFoundException {

        when(stockRepository.findAll()).thenThrow(StockNotFoundException.class);

        simpleStockService.calculateGBCEAllShareIndex();
    }


}
