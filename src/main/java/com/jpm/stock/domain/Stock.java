package com.jpm.stock.domain;

/**
 * Represents the Stock in the application
 */
public class Stock {

    private final StockType stockType;
    private final StockSymbol stockSymbol;
    private double lastDividend = 0.0;
    private double fixedDividend = 0.0;
    private double parValue = 0.0;
    private double tickerPrice = 0.0;

    public Stock(StockType stockType, StockSymbol stockSymbol) {
        this.stockType = stockType;
        this.stockSymbol = stockSymbol;
    }

    public Stock(StockSymbol symbol, StockType type, Double lastDividend, Double fixedDividend, Double parValue) {
        this.stockSymbol = symbol;
        this.stockType = type;
        this.setLastDividend(lastDividend);
        this.setFixedDividend(fixedDividend);
        this.setParValue(parValue);
    }

    public StockType getStockType() {
        return stockType;
    }

    public StockSymbol getStockSymbol() {
        return stockSymbol;
    }

    public double getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(double lastDividend) {
        this.lastDividend = lastDividend;
    }

    public double getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public double getParValue() {
        return parValue;
    }

    public void setParValue(double parValue) {
        this.parValue = parValue;
    }


    public double getTickerPrice() {
        return tickerPrice;
    }

    public void setTickerPrice(double tickerPrice) {
        this.tickerPrice = tickerPrice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stock{");
        sb.append("stockType=").append(stockType);
        sb.append(", stockSymbol='").append(stockSymbol).append('\'');
        sb.append(", lastDividend=").append(lastDividend);
        sb.append(", fixedDividend=").append(fixedDividend);
        sb.append(", parValue=").append(parValue);
        sb.append(", tickerPrice=").append(tickerPrice);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;

        Stock stock = (Stock) o;

        if (Double.compare(stock.fixedDividend, fixedDividend) != 0) return false;
        if (Double.compare(stock.lastDividend, lastDividend) != 0) return false;
        if (Double.compare(stock.parValue, parValue) != 0) return false;
        if (Double.compare(stock.tickerPrice, tickerPrice) != 0) return false;
        if (!stockSymbol.equals(stock.stockSymbol)) return false;
        if (stockType != stock.stockType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = stockType.hashCode();
        result = 31 * result + stockSymbol.hashCode();
        temp = Double.doubleToLongBits(lastDividend);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(fixedDividend);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(parValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(tickerPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
