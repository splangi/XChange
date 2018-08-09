package org.knowm.xchange.bitfinex.common.service;

import android.util.Base64;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class BitfinexPayloadDigest implements ParamsDigest {

  @Override
  public synchronized String digestParams(RestInvocation restInvocation) {

    String postBody = restInvocation.getRequestBody();
    return new String(Base64.encode(postBody.getBytes(), Base64.NO_WRAP));
  }
}
