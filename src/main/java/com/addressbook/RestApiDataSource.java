package com.addressbook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class RestApiDataSource implements AddressBookDataSource {
    private final String serverUrl;
    private final Gson gson;

    public RestApiDataSource(String serverUrl) {
        this.serverUrl = serverUrl;
        
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        builder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE));
        
        this.gson = builder.create();
    }

    @Override
    public List<Contact> readData() {
        Response response = RestAssured.get(serverUrl + "/contacts");
        String jsonString = response.getBody().asString();
        Contact[] contacts = gson.fromJson(jsonString, Contact[].class);
        return Arrays.asList(contacts);
    }

    @Override
    public void writeData(List<Contact> contacts) {
        for (Contact contact : contacts) {
            String jsonContact = gson.toJson(contact);
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(jsonContact)
                    .post(serverUrl + "/contacts");
        }
    }
}
