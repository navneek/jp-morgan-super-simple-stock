package com.jpm.stock.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Test the Trade object
 */
public class TestTrade {

    private Trade testObject;

    @Before
    public void setUp() {
        testObject = new Trade(TradeIndicator.BUY, new Stock(StockType.COMMON, StockSymbol.ALE.name()));
    }

    @Test
    public void testTradeCreation(){
        Assert.assertNotNull(testObject);
        Trade another = new Trade(TradeIndicator.BUY, new Stock(StockType.COMMON, StockSymbol.ALE.name()));
        Assert.assertTrue(testObject.equals(another));
        Assert.assertFalse(testObject.equals(new Trade(TradeIndicator.SELL, new Stock(StockType.PREFERRED, StockSymbol.JOE.name()))));
    }

    @Test
    public void testMutation(){
        testObject.setTime(new Date());
        testObject.setShareQuantity(new Double(5.0));

        Assert.assertTrue(TradeIndicator.BUY.equals(testObject.getIndicator()));
        Assert.assertEquals(new Stock(StockType.COMMON, StockSymbol.ALE.name()), testObject.getStock());
        Assert.assertTrue(Double.compare(new Double(5.0), testObject.getShareQuantity())== 0);
        Assert.assertTrue(Double.compare(new Double(0.0), testObject.getPrice())== 0);
    }
}
