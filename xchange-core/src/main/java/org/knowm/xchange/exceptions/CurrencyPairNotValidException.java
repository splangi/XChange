package org.knowm.xchange.exceptions;

import org.knowm.xchange.currency.CurrencyPair;

/**
 * Exception indicating that a request was made with a {@link CurrencyPair}
 * that is not supported on this exchange.
 *
 * @author bryant_harris
 */
public class CurrencyPairNotValidException extends ExchangeException {
  private CurrencyPair currencyPair;

    public CurrencyPairNotValidException(String message, Throwable cause, CurrencyPair currencyPair) {
    super(message, cause);
    this.currencyPair = currencyPair;
  }

  public CurrencyPairNotValidException(String message, Throwable cause) {
    super(message, cause);
  }

  public CurrencyPairNotValidException(String message) {
    super(message);
  }

    public CurrencyPairNotValidException(String message, CurrencyPair currencyPair) {
    super(message);
    this.currencyPair = currencyPair;
  }

  public CurrencyPairNotValidException(Throwable cause) {
    super(cause);
  }

    public CurrencyPairNotValidException(Throwable cause, CurrencyPair currencyPair) {
    super(currencyPair + " Is not valid for this exchange", cause);
    this.currencyPair = currencyPair;
  }

    public CurrencyPairNotValidException(CurrencyPair currencyPair) {
    this(currencyPair + " Is not valid for this exchange");
    this.currencyPair = currencyPair;
  }

  /**
   * @return The currency pair that caused the exception.
   */
  public CurrencyPair getCurrencyPair() {
    return currencyPair;
  }
}
