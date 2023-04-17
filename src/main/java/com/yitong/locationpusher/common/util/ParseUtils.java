package com.yitong.locationpusher.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParseUtils {

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

}
