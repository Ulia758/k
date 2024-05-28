package org.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Main {
    public static void main(String[] args)  throws UnknownHostException  {
        String botToken = "6888552919:AAEh6PzvS89iR12NC6iY-lxpfxP4II0JlMM";
        try {
            URL url = new URL("https://api.telegram.org/bot6888552919:AAEh6PzvS89iR12NC6iY-lxpfxP4II0JlMM/getMe");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            String responseBody = response.toString();
            Pattern pattern = Pattern.compile("\"text\":\"(.*?)\"");
            Matcher matcher = pattern.matcher(responseBody);

            if (matcher.find()) {
                String message = matcher.group(1);

                // Send message back to user
                URL sendMessageUrl = new URL("https://api.telegram.org/bot6481669749:AAFEUwm2puWGhkUjrcj3p_ErB4AO9q9GGQo/sendMessage?chat_id=371048147&text=" + message);
                HttpURLConnection sendMessageConnection = (HttpURLConnection) sendMessageUrl.openConnection();
                sendMessageConnection.setRequestMethod("GET");

                BufferedReader sendMessageIn = new BufferedReader(new InputStreamReader(sendMessageConnection.getInputStream()));
                sendMessageIn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}