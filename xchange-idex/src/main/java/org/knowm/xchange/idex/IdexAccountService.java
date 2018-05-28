package org.knowm.xchange.idex;

import static org.knowm.xchange.idex.IdexExchange.Companion.safeParse;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.idex.dto.*;
import org.knowm.xchange.idex.service.ReturnCompleteBalancesApi;
import org.knowm.xchange.idex.service.ReturnDepositsWithdrawalsApi;
import org.knowm.xchange.idex.service.WithdrawApi;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import si.mazi.rescu.RestProxyFactory;

public class IdexAccountService implements AccountService {
  private IdexExchange idexExchange;
  private String apiKey = idexExchange.getExchangeSpecification().getApiKey();
  private ReturnCompleteBalancesApi returnCompleteBalancesApi =
      RestProxyFactory.createProxy(
          ReturnCompleteBalancesApi.class, idexExchange.getExchangeSpecification().getSslUri());
  private ReturnDepositsWithdrawalsApi returnDepositsWithdrawalsApi =
      RestProxyFactory.createProxy(
          ReturnDepositsWithdrawalsApi.class,
          idexExchange.getDefaultExchangeSpecification().getSslUri());
  private WithdrawApi withdrawApi =
      RestProxyFactory.createProxy(
          WithdrawApi.class, idexExchange.getDefaultExchangeSpecification().getSslUri());

  public IdexAccountService(IdexExchange idexExchange) {
    this.idexExchange = idexExchange;
  }

  public AccountInfo getAccountInfo() {
    AccountInfo ret = null;
    try {
      String apiKey = idexExchange.getExchangeSpecification().getApiKey();
      String s = apiKey.substring(0, 6) + "…";
      ReturnCompleteBalancesResponse returnBalancesPost;
      ret = null;
      returnBalancesPost =
          returnCompleteBalancesApi.completeBalances(new CompleteBalancesReq().address(apiKey));

      ret =
          new AccountInfo(
              new Wallet(
                  s,
                  returnBalancesPost
                      .entrySet()
                      .stream()
                      .map(
                          entry ->
                              new Balance(
                                  new Currency(entry.getKey()),
                                  null,
                                  entry.getValue().getAvailable(),
                                  entry.getValue().getOnOrders()))
                      .collect(Collectors.toList())));

    } catch (Exception ignored) {
      ignored.printStackTrace();
    }
    return ret;
  }

  public String requestDepositAddress(Currency currency, String... args) {
    return idexExchange.getExchangeSpecification().getApiKey();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) {
    List<FundingRecord> ret = null;
    if (!(params instanceof IdexTradeHistoryParams)) {
      throw new Error(
          "getFundingHistory requires " + IdexTradeHistoryParams.class.getCanonicalName());
    } else {
      try {
        ret =
            mutableList(
                returnDepositsWithdrawalsApi.fundingHistory((DepositsWithdrawalsReq) params));
      } catch (Exception e) {

      }
    }
    return ret;
  }

  private final List<FundingRecord> mutableList(
      ReturnDepositsWithdrawalsResponse returnDepositsWithdrawalsPost) {
    return (List<FundingRecord>)
        Arrays.asList(
                returnDepositsWithdrawalsPost
                    .getWithdrawals()
                    .stream()
                    .map(
                        fundingLedger ->
                            new FundingRecord(
                                apiKey,
                                new Date(Long.parseLong(fundingLedger.getTimestamp()) * 1000),
                                new Currency(fundingLedger.getCurrency()),
                                safeParse(fundingLedger.getAmount()),
                                fundingLedger.getTransactionHash(),
                                fundingLedger.getDepositNumber(),
                                Type.WITHDRAWAL,
                                Status.resolveStatus(fundingLedger.getStatus()),
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                ""))
                    .collect(Collectors.toList()),
                returnDepositsWithdrawalsPost
                    .getDeposits()
                    .stream()
                    .map(
                        fundingLedger1 ->
                            new FundingRecord(
                                apiKey,
                                new Date(Long.parseLong(fundingLedger1.getTimestamp()) * 1000),
                                new Currency(fundingLedger1.getCurrency()),
                                safeParse(fundingLedger1.getAmount()),
                                fundingLedger1.getTransactionHash(),
                                fundingLedger1.getDepositNumber(),
                                Type.WITHDRAWAL,
                                Status.resolveStatus(fundingLedger1.getStatus()),
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                ""))
                    .collect(Collectors.toList()))
            .stream()
            .flatMap(List::stream)
            .sorted(Comparator.comparing(FundingRecord::getDate));
  }

  public TradeHistoryParams createFundingHistoryParams() {
    return new IdexTradeHistoryParams();
  }

  public String withdrawFunds(WithdrawFundsParams w) {
    String ret = "error";
    if (w instanceof IdexWithdraw) {
      WithdrawResponse withdraw = null;
      try {
        withdraw = withdrawApi.withdraw((WithdrawReq) w);
      } catch (Exception e) {
        e.printStackTrace();
      }
      ret = withdraw.toString();
    } else {
      throw new Error(
          "withdraw method requires "
              + org.knowm.xchange.idex.IdexWithdraw.class.getCanonicalName());
    }
    return ret;
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) {
    throw new org.knowm.xchange.exceptions.NotAvailableFromExchangeException();
  }
}
