package com.jpm.stock.service;

import com.jpm.stock.dao.IStockRepository;
import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.exception.StockNotFoundException;
import com.jpm.stock.exception.StockServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple Implementation of stock service
 */
@Service
public class SimpleStockServiceImpl implements ISimpleStockService {

    private static final Logger LOG = Logger.getLogger("SimpleStockServiceImpl");

    @Autowired
    private IStockRepository stockRepository;

    @Autowired
    ITradeService tradeService;

    @Override
    public Double calculateDividendYield(String stockSymbol,
                                         double price) throws StockServiceException, StockNotFoundException {

        double dividendYield = 0;
        //Find the stock by symbol
        final Stock stock = stockRepository.findStockBySymbol(stockSymbol);
        if (stock == null) {
            throw new StockNotFoundException("Cannot find stock for symbol " + stockSymbol);
        }
        try {
            final StockType stockType = stock.getStockType();
            switch (stockType) {
                case COMMON:
                    dividendYield = stock.getLastDividend() / price;
                    break;
                case PREFERRED:
                    dividendYield = (stock.getFixedDividend() * stock.getParValue()) / price;
                    break;
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw new StockServiceException("Cannot calculate dividend yield");
        }
        return dividendYield;

    }

    @Override
    public Double calculatePERatio(String stockSymbol,
                                   double price) throws StockServiceException, StockNotFoundException {
        final Stock stock = stockRepository.findStockBySymbol(stockSymbol);
        try {
            return price / stock.getLastDividend();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw new StockServiceException("Cannot calculate PE ratio");
        }
    }

    @Override
    public double calculateVolumeWeightedStockPrice(String symbol) throws StockNotFoundException {

        final Stock stock = stockRepository.findStockBySymbol(symbol);
        final List<Trade> trades = tradeService.findAllTradesByStock(stock);
        double totalVolume = 0.0;
        double totalQuantity = 0.0;

        List<Trade> filterByPeriod = filterByPeriod(trades, 15);
        for (Trade trade : filterByPeriod) {
            totalQuantity = totalQuantity + trade.getShareQuantity();
            totalVolume = totalVolume + (trade.getShareQuantity() * trade.getPrice());
        }
        return Math.round(totalVolume / totalQuantity);
    }


    @Override
    public double calculateAllShareIndex(List<Stock> stocks) {
        if (stocks == null || stocks.size() == 0) {
            throw new IllegalArgumentException("Cannot calculate all share index for empty stocks.");
        }
        double sum = 1.0;
        for (Stock stock : stocks) {
            sum = sum * stock.getTickerPrice();
        }
        return Math.round(StrictMath.pow(sum, 1.0 / stocks.size()));
    }

    private List<Trade> filterByPeriod(List<Trade> trades, int timeInMinutes) {
        List<Trade> list = new ArrayList<>();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -(timeInMinutes * 60));
        Date from = calendar.getTime();
        for (Trade trade : trades) {
            if (trade.getTime().equals(from) || trade.getTime().equals(now)  ||  (trade.getTime().after(from) && trade.getTime().before(now))) {
                list.add(trade);
            }
        }
        return list;
    }

}
