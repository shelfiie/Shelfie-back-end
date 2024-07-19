package com.mieker.ifpr.shelfie.config;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Converter
public class AuthorsListConverter implements AttributeConverter<List<String>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> authors) {
        if (authors == null || authors.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(authors);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting list to JSON", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String authorsJSON) {
        if (authorsJSON == null || authorsJSON.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(authorsJSON, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to list", e);
        }
    }
}
