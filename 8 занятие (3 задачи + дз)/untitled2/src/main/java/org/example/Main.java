package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        String botToken = "6888552919:AAEh6PzvS89iR12NC6iY-lxpfxP4II0JlMM";
        try {
            URL url = new URL("https://api.telegram.org/bot6888552919:AAEh6PzvS89iR12NC6iY-lxpfxP4II0JlMM/getMe");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String responseBody = response.toString();
            Pattern pattern = Pattern.compile("\"text\":\"(.*?)\"");
            Matcher matcher = pattern.matcher(responseBody);
            while (matcher.find()) {
                String messageText = matcher.group(1);
                System.out.println("Получено сообщение: " + messageText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}