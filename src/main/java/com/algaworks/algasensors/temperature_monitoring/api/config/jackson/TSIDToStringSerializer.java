package com.algaworks.algasensors.temperature_monitoring.api.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.hypersistence.tsid.TSID;

import java.io.IOException;

public class TSIDToStringSerializer extends JsonSerializer<TSID> {
    // Passa o TSID para String base 32
    @Override
    public void serialize(TSID value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}