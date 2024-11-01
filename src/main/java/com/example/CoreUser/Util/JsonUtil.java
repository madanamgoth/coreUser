package com.example.CoreUser.Util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static <T> T parseJson(String json, Class<T> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, clazz);
    }

    public static String toJson(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
