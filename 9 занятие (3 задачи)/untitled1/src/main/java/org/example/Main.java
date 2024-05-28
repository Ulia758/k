package org.example;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            JsonObject postObject = new JsonObject();
            postObject.addProperty("title", "Пост");
            postObject.addProperty("body", "Новый пост");
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(postObject.toString());
            osw.flush();
            osw.close();
            os.close();
            InputStream responseStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            int postId = jsonResponse.get("id").getAsInt();
            String postTitle = jsonResponse.get("title").getAsString();
            String postBody = jsonResponse.get("body").getAsString();
            System.out.println("Созданный пост: ");
            System.out.println("ID: " + postId);
            System.out.println("Заголовок: " + postTitle);
            System.out.println("Содержимое: " + postBody);
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
