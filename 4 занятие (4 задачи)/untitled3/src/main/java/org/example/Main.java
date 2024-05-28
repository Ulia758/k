package org.example;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Main {
    private static final Map<String, String> map = new HashMap<>();
    static {
        map.put("1", "один");
        map.put("2", "два");
        map.put("3", "три");
        map.put("4", "четыре");
        map.put("5", "пять");
        map.put("6", "шесть");
        map.put("7", "семь");
        map.put("8", "восемь");
        map.put("9", "девять");
        map.put("10", "десять");
    }

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            String izmtext = zamena(content.toString());
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write(izmtext);
            writer.close();
            System.out.println("Успешно! Числа заменены на текст");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String zamena(String text) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            text = text.replaceAll("\\b" + entry.getKey() + "\\b", entry.getValue());
        }
        return text;
    }
}