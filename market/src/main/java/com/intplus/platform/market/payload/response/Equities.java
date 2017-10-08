package com.intplus.platform.market.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Equities {

    @JsonProperty("equities")
    private final List<Equity> equities = new ArrayList<Equity>();

    public List<Equity> getEquities() {
        return equities;
    }
}
