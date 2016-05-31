package com.jpm.stock.service;

import com.jpm.stock.dao.IStockRepository;
import com.jpm.stock.domain.Stock;
import com.jpm.stock.domain.StockType;
import com.jpm.stock.domain.Trade;
import com.jpm.stock.exception.StockNotFoundException;
import com.jpm.stock.exception.StockServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Double dividendYield = calculateDividendYield(stockSymbol, price);
        try {
            return price / dividendYield;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw new StockServiceException("Cannot calculate PE ratio");
        }
    }

    @Override
    public boolean recordTrade(Trade trade) throws StockServiceException {
        return tradeService.record(trade);
    }

    @Override
    public Double calculateVolumeWeightedStockPrice() throws StockServiceException {
        List<Trade> trades = tradeService.getTradesFromLast(15);
        return null;
    }


    @Override
    public double calculateAllShareIndex(List<Stock> stocks) throws StockServiceException {
        double sum = 0;
        if (stocks == null || stocks.size() == 0) {
            throw new IllegalArgumentException("Cannot calculate all share index for empty stocks.");
        }
        for (Stock stock : stocks) {
            sum = sum * stock.getTickerPrice();
        }
        return StrictMath.pow(sum, 1.0 / stocks.size());
    }


}
