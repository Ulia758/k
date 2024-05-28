package org.example;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String token = "6888552919:AAEh6PzvS89iR12NC6iY-lxpfxP4II0JlMM";
        String urlString = "https://api.telegram.org/bot6888552919:AAEh6PzvS89iR12NC6iY-lxpfxP4II0JlMM/getMe";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}