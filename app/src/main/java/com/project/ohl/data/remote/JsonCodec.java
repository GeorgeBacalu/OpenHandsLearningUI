package com.project.ohl.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class JsonCodec {
    private JsonCodec() {}

    public static Gson create() {
        return new GsonBuilder().registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
            @Override public void write(JsonWriter writer, LocalDate value) throws IOException {
                writer.value(value.toString());
            }
            @Override public LocalDate read(JsonReader reader) throws IOException {
                try {
                    return LocalDate.parse(reader.nextString());
                } catch (DateTimeParseException exception) {
                    throw new JsonParseException("Expected an ISO-8601 date", exception);
                }
            }
        }.nullSafe()).create();
    }
}
