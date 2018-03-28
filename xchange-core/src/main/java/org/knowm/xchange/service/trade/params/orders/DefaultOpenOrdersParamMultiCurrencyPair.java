package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;

import java.util.Collection;
import java.util.Collections;

public class DefaultOpenOrdersParamMultiCurrencyPair implements OpenOrdersParamMultiCurrencyPair {

  private Collection<CurrencyPair> pairs = Collections.emptySet();

  @Override
  public Collection<CurrencyPair> getCurrencyPairs() {
    return pairs;
  }

    @Override
    public void setCurrencyPairs(Collection<CurrencyPair> value) {
        pairs = value;
    }
}
