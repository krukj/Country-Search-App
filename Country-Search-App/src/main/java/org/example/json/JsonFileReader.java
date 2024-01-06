package org.example.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.country.Country;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileReader {
    public static void main(String[] args) throws IOException {
        List<Country> countries = createCountriesList("Country-Search-App/response.json");
    }

    public static List<Country> createCountriesList(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        List<Country> countries = List.of(objectMapper.readValue(new File(filePath), Country[].class));

        return countries;
    }
}