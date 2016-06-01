package com.jpm.stock.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the Stock
 */
public class TestStock {

    private Stock jpMorgan;

    @Before
    public void setUp() {
        jpMorgan = new Stock(StockType.COMMON, StockSymbol.ALE.name());
    }

    @Test
    public void testStock(){
        Assert.assertNotNull(jpMorgan);
        Assert.assertTrue(jpMorgan.equals(new Stock(StockType.COMMON, StockSymbol.ALE.name())));

        Stock gin = new Stock(StockType.COMMON, StockSymbol.GIN.name());
        Assert.assertFalse(jpMorgan.equals(gin));

    }

    @Test
    public void testMutation(){
        jpMorgan.setFixedDividend(new Double(12.0));
        jpMorgan.setLastDividend(new Double(5.0));
        jpMorgan.setParValue(new Double(10.0));
        jpMorgan.setTickerPrice(new Double(20.0));

        Assert.assertTrue(Double.compare(new Double(12.0), jpMorgan.getFixedDividend())== 0);
        Assert.assertTrue(Double.compare(new Double(5.0), jpMorgan.getLastDividend())== 0);
        Assert.assertTrue(Double.compare(new Double(10.0), jpMorgan.getParValue())== 0);
        Assert.assertTrue(Double.compare(new Double(20.0), jpMorgan.getTickerPrice())== 0);
    }
}
