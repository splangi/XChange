package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.currency.CurrencyPair;

import java.util.Collection;

public interface TradeHistoryParamMultiCurrencyPair extends TradeHistoryParams {

  Collection<CurrencyPair> getCurrencyPairs();

    void setCurrencyPairs(Collection<CurrencyPair> pairs);
}
