package com.intplus.platform.http;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JsonProvider;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

import static com.sun.xml.internal.ws.commons.xmlutil.Converter.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class JsonElementHttpMessageConverter extends AbstractHttpMessageConverter<JsonElement> {

    private final JsonProvider jsonProvider = Configuration.defaultConfiguration().jsonProvider();

    public JsonElementHttpMessageConverter() {
        super(APPLICATION_JSON, new MediaType("application", "*+json"));
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return JsonElement.class == aClass;
    }

    @Override
    protected JsonElement readInternal(Class<? extends JsonElement> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return new JsonElement(jsonProvider.parse(httpInputMessage.getBody(), UTF_8));
    }

    @Override
    protected void writeInternal(JsonElement jsonElement, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        throw new UnsupportedOperationException();
    }
}
