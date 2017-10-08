package com.intplus.platform.market.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/*
ToDo : Everyone needs; Move to common package.
 */
@JsonInclude(NON_NULL)
public class Equity {

    // Exchange along with a script defines an equity.
    private final String exchange;
    private final String script;
    private String price;
    private String high52;
    private String low52;

    public Equity(@JsonProperty("exchange") String exchange,
                  @JsonProperty("script") String script) {
        this.exchange = exchange;
        this.script = script;
    }

    @JsonProperty("exchange")
    public String getExchange() {
        return exchange;
    }

    @JsonProperty("script")
    public String getScript() {
        return script;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHigh52() {
        return high52;
    }

    public void setHigh52(String high52) {
        this.high52 = high52;
    }

    public String getLow52() {
        return low52;
    }

    public void setLow52(String low52) {
        this.low52 = low52;
    }
}
