package com.sauzny.jackson.custom;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.sauzny.jackson.ApmOne;

public class CustomDeserializer extends StdDeserializer<ApmOne> {

    protected CustomDeserializer(Class<ApmOne> vc) {
        super(vc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ApmOne deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        ApmOne person = new ApmOne();
        int age = (Integer) ((IntNode) node.get("age")).numberValue();
        String name = node.get("name").asText();
        person.setAge(age);
        person.setName(name);
        return person;
    }
}
