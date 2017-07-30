package com.progressoft.workshop.upload.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestJsonMapper<T> implements JsonMapper<T> {

    private final ObjectMapper mapper;
    private final Class<T> type;

    public TestJsonMapper(ObjectMapper mapper, Class<T> type) {
        this.mapper = mapper;
        this.type = type;
    }

    @Override
    public String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public T fromJson(String json) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            return null;
        }
    }
}
