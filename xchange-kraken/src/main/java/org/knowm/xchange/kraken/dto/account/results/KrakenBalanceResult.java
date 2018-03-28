package org.knowm.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.knowm.xchange.kraken.dto.KrakenResult;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Benedikt
 */
public class KrakenBalanceResult extends KrakenResult<Map<String, BigDecimal>> {

  /**
   * Constructor
   *
   * @param error  List of errors
   * @param result Recent trades
   */
  public KrakenBalanceResult(@JsonProperty("error") String[] error, @JsonProperty("result") Map<String, BigDecimal> result) {

    super(result, error);
  }

}
