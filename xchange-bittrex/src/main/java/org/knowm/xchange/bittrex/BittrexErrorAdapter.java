package org.knowm.xchange.bittrex;

import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author walec51 */
public class BittrexErrorAdapter {

  public static ExchangeException adapt(BittrexException e) {
    String message = e.getMessage();
    if (message.isEmpty()) {
      return new ExchangeException("Operation failed without any error message");
    }
    switch (message) {
      case "INVALID_MARKET":
        return new CurrencyPairNotValidException();
    }
    return new ExchangeException(message);
  }
}
