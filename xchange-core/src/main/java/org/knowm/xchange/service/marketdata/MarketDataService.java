package org.knowm.xchange.service.marketdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseService;

/**
 * <p>
 * Interface to provide the following to {@link Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to explore the market data</li>
 * </ul>
 * <p>
 * The implementation of this service is expected to be based on a client polling mechanism of some kind
 * </p>
 */
public interface MarketDataService extends BaseService {

  /**
   * <p>
   * Get a ticker representing the current exchange rate
   * </p>
   *
   * @return The Ticker, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   */
  Ticker getTicker(CurrencyPair currencyPair,
      Object... args) throws IOException;


  default List<Ticker> getTickers(CurrencyPair... currencyPairs) throws IOException{
    ExecutorService es = Executors.newCachedThreadPool();
    List<Future<Ticker>> callableList = new ArrayList<>();
    for (CurrencyPair currencyPair : currencyPairs){
      Callable<Ticker> callable = new Callable<Ticker>() {
        @Override
        public Ticker call() throws Exception {
          return getTicker(currencyPair, (Object[]) null);
        }
      };
      callableList.add(es.submit(callable));
    }
    es.shutdown();
    try {
      es.awaitTermination(60, TimeUnit.SECONDS);
      List<Ticker> tickers = new ArrayList<>();
      for (Future<Ticker> future : callableList){
        tickers.add(future.get());
      }
      return tickers;
    } catch (InterruptedException | ExecutionException e) {
      throw new IOException(e);
    }
  }

  /**
   * <p>
   * Get an order book representing the current offered exchange rates (market depth)
   * </p>
   *
   * @param args Optional arguments. Exchange-specific
   * @return The OrderBook, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   */
  OrderBook getOrderBook(CurrencyPair currencyPair,
      Object... args) throws IOException;

  /**
   * <p>
   * Get the trades recently performed by the exchange
   * </p>
   *
   * @param args Optional arguments. Exchange-specific
   * @return The Trades, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   */
  Trades getTrades(CurrencyPair currencyPair,
      Object... args) throws IOException;

}
