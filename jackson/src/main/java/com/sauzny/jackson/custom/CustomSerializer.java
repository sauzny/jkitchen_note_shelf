package com.sauzny.jackson.custom;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sauzny.jackson.entity.ApmOne;

public class CustomSerializer extends StdSerializer<ApmOne> {

    protected CustomSerializer(Class<ApmOne> t) {
        super(t);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void serialize(ApmOne person, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("age", person.getAge());
        jgen.writeStringField("name", person.getName());
        jgen.writeEndObject();
    }
}
