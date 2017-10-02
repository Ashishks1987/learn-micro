package com.intplus.platform.market;

import com.intplus.platform.http.JsonElement;
import com.intplus.platform.market.payload.request.EquityQuoteRequest;
import com.intplus.platform.market.payload.request.EquityQuoteRequests;
import com.intplus.platform.market.payload.response.Equities;
import com.intplus.platform.market.payload.response.Equity;
import com.intplus.platform.utils.Async;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestOperations;
import rx.Observable;
import rx.Single;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class MarketController {

    @Resource
    private AsyncRestOperations rest;

    private static String yahooApi = "https://query1.finance.yahoo.com/v10/finance/quoteSummary/SCRIPT.EXCHANGE?" +
            "formatted=true&crumb=RuvLC4rq1a/&lang=en-US&region=US&modules=" +
            "price,summaryDetail&corsDomain=finance.yahoo.com";

    @RequestMapping(value = "/quotes", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<Equities>> getQuotes(
            @RequestBody EquityQuoteRequests quoteRequests) throws ExecutionException, InterruptedException {

        List<Observable<Equity>> requests = new ArrayList<>();

        for (EquityQuoteRequest eqQuoteRequest : quoteRequests.getEqQuoteRequests()) {
            requests.add(askYahoo(eqQuoteRequest));
        }

        return Observable.merge(requests)
                .collect(Equities::new, (equites, equity) -> equites.getEquities().add(equity))
                .map(ResponseEntity::ok)
                .toSingle();
    }

    private Observable<Equity> askYahoo(EquityQuoteRequest eqQuoteRequest) {
        String script = eqQuoteRequest.getEqScript();
        String exchange = eqQuoteRequest.getEqExchange();
        String yahooUrl = yahooApi.replace("SCRIPT", script)
                                    .replace("EXCHANGE", exchange);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", APPLICATION_JSON_VALUE);
        ListenableFuture<ResponseEntity<JsonElement>> futureYahoo = rest.exchange(yahooUrl, HttpMethod.GET,
                new HttpEntity<>(headers), JsonElement.class);

        return Async.toSingle(futureYahoo).toObservable()
                .onErrorResumeNext(this::skipError)
                .flatMap(entity -> processYahooResponse(entity, script, exchange));
    }

    private Observable<ResponseEntity<JsonElement>> skipError(Throwable throwable) {
        System.out.println(">>>>>>>>>>>>>>>> Error! : "+throwable.getMessage());
        return Observable.empty();
    }

    private Observable<Equity> processYahooResponse(ResponseEntity<JsonElement> yahooResponse, String script, String exchange){
        JsonElement yResponse = yahooResponse.getBody();
        String quote = yResponse.read("$.quoteSummary.result[0].price.regularMarketPrice.fmt");
        Equity equity = new Equity(script, exchange);
        equity.setPrice(quote);
        return Observable.just(equity);
    }
}
