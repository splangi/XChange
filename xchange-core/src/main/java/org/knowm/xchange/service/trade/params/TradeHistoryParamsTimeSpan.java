package org.knowm.xchange.service.trade.params;

import org.knowm.xchange.service.trade.TradeService;

import java.util.Date;

/**
 * Parameters type for {@link TradeService#getTradeHistory(TradeHistoryParams)} with start and end timestamps.
 */
public interface TradeHistoryParamsTimeSpan extends TradeHistoryParams {

  Date getStartTime();

    void setStartTime(Date startTime);

  Date getEndTime();

    void setEndTime(Date endTime);

}
