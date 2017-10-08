package com.intplus.platform.market.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EquityQuoteRequests {

    private List<EquityQuoteRequest> eqQuoteRequests;

    public EquityQuoteRequests(@JsonProperty("quote_requests") List<EquityQuoteRequest> eqQuoteRequests) {
        this.eqQuoteRequests = eqQuoteRequests;
    }

    public List<EquityQuoteRequest> getEqQuoteRequests() {
        return eqQuoteRequests;
    }
}
