package com.intplus.platform.market.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EquityQuoteRequest {

    private String eqScript;
    private String eqExchange;

    public EquityQuoteRequest(@JsonProperty("script") String eqScript,
                              @JsonProperty("exchange") String eqExchange) {
        this.eqScript = eqScript;
        this.eqExchange = eqExchange;
    }

    public String getEqScript() {
        return eqScript;
    }

    public String getEqExchange() {
        return eqExchange;
    }
}
