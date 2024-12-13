package com.skypro.bank_star.request;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.skypro.bank_star.exception.DoesNotEnumException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class RulesQueryDeserializer extends JsonDeserializer<RulesQuery> {
    private final Logger logger = LoggerFactory.getLogger(RulesQueryDeserializer.class);

    @Override
    public RulesQuery deserialize(JsonParser p, DeserializationContext src) throws IOException {
        String value = p.getText().toUpperCase();
        for (RulesQuery enumValue : RulesQuery.values()) {
            if (enumValue.name().equals(value)) {
                return enumValue;
            }
        }
        logger.error("Error query must be valid ENUM value");
        throw new DoesNotEnumException("Query \"" + value + "\" should be one of the values in RulesQueryENUM: " + Arrays.toString(RulesQuery.values()));
    }

}