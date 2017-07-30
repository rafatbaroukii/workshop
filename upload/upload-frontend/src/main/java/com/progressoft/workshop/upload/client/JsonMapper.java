package com.progressoft.workshop.upload.client;

public interface JsonMapper<T>{
    String toJson(T object);
    T fromJson(String json);
}
