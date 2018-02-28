package org.knowm.xchange.utils;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TickerUtils {


    public static List<Ticker> getTickers(MarketDataService service, CurrencyPair... currencyPairs) throws IOException{
        ExecutorService es = Executors.newCachedThreadPool();
        List<Future<Ticker>> callableList = new ArrayList<>();
        for (CurrencyPair currencyPair : currencyPairs){
            Callable<Ticker> callable = new Callable<Ticker>() {
                @Override
                public Ticker call() throws Exception {
                    return service.getTicker(currencyPair, (Object[]) null);
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

}
