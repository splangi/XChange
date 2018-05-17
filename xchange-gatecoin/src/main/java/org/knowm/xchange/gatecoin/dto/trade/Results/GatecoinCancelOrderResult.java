package org.knowm.xchange.gatecoin.dto.trade.Results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.gatecoin.dto.GatecoinResult;
import org.knowm.xchange.gatecoin.dto.marketdata.ResponseStatus;

/** @author sumedha */
public class GatecoinCancelOrderResult extends GatecoinResult {

  @JsonCreator
  public GatecoinCancelOrderResult(@JsonProperty("responseStatus") ResponseStatus responseStatus) {
    super(responseStatus);
  }
}
