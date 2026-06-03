package com.addressbook;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonFileDataSource implements AddressBookDataSource {
    private final Path filePath;
    private final Gson gson;

    public JsonFileDataSource(String fileName) {
        this.filePath = Paths.get(fileName);
        
        // Custom adapter for LocalDate
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        builder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE));
        
        this.gson = builder.setPrettyPrinting().create();
    }

    @Override
    public List<Contact> readData() {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(filePath.toFile())) {
            Type listType = new TypeToken<ArrayList<Contact>>(){}.getType();
            List<Contact> contacts = gson.fromJson(reader, listType);
            if (contacts == null) return new ArrayList<>();
            return contacts;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void writeData(List<Contact> contacts) {
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            gson.toJson(contacts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
