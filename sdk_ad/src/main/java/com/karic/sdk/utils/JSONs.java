package com.karic.sdk.utils;

import com.google.gson.Gson;

public final class JSONs {
    private static final Gson _instance = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        return _instance.fromJson(json, clazz);
    }
}