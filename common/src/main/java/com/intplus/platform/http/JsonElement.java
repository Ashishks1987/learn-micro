package com.intplus.platform.http;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.Predicate;

public class JsonElement {

    private final Object body;
    private Configuration configuration;

    public JsonElement(Object body) {
        this.body = body;
        configuration = Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS);
    }

    public <T> T read(String readPath, Predicate... filters){
        return JsonPath.using(configuration).parse(body).read(readPath, filters);
    }
}
