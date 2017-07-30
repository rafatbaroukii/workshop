package com.progressoft.workshop.upload.client.presenters;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.progressoft.workshop.upload.client.JsonMapper;

public class GwtJacksonJsonMapper<T, M extends ObjectMapper<T>> implements JsonMapper<T> {

    private final M mapper;

    public GwtJacksonJsonMapper(M mapper) {
        this.mapper = mapper;
    }

    @Override
    public String toJson(T object) {
        return mapper.write(object);
    }

    @Override
    public T fromJson(String json) {
        return mapper.read(json);
    }
}
