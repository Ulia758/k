package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String DATABASE_URL = "http://localhost/testBase/hs/api/financial_data/";
    public JsonArray selectRecordsById(long chatId) {
        JsonArray resultArray;
        try {
            URL url = new URL(DATABASE_URL + chatId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                resultArray = JsonParser.parseString(response.toString()).getAsJsonArray();
                connection.disconnect();
                return resultArray;
            } else {
                System.out.println(responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean addRecord(JsonObject record) {
        try {
            URL url = new URL(DATABASE_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(record.toString());
            osw.flush();
            osw.close();
            os.close();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}