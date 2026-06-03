package com.addressbook;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Type;

public class AddressBookRESTService implements IAddressBookDataService {

    private static final String REST_URL = "http://localhost:3000/contacts";

    @Override
    public void writeData(List<Contact> contacts) {
        for (Contact contact : contacts) {
            addContactToServer(contact);
        }
    }

    @Override
    public void readData() {
        List<Contact> contacts = getContactsFromServer();
        System.out.println("Contacts read from REST Server: ");
        contacts.forEach(System.out::println);
    }

    public List<Contact> getContactsFromServer() {
        List<Contact> contacts = new ArrayList<>();
        try {
            URL url = new URL(REST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
                Gson gson = new Gson();
                Type contactListType = new TypeToken<List<Contact>>(){}.getType();
                contacts = gson.fromJson(reader, contactListType);
                reader.close();
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void addContactToServer(Contact contact) {
        try {
            URL url = new URL(REST_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();
            String jsonInputString = gson.toJson(contact);

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int code = connection.getResponseCode();
            System.out.println("REST POST Response Code: " + code);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
