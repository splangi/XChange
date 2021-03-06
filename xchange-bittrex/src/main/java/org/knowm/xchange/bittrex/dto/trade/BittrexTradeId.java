package org.knowm.xchange.bittrex.dto.trade;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({"uuid"})
public class BittrexTradeId {

  @JsonProperty("uuid")
  private String uuid;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("uuid")
  public String getUuid() {

    return uuid;
  }

  @JsonProperty("uuid")
  public void setUuid(String uuid) {

    this.uuid = uuid;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {

    return "BittrexTradeId [uuid=" + uuid + ", additionalProperties=" + additionalProperties + "]";
  }
}
