package com.project.ohl.data.remote;

import com.google.gson.Gson;

public final class JsonCodec {
    private JsonCodec() {}

    public static Gson create() {
        return new Gson();
    }
}
