package org.example;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) {
        File fromDirectory = new File("C:\\Users\\Student.DESKTOP-QKGSV6O\\Documents\\Колосова");
        File inDirectory = new File("C:\\Users\\Student.DESKTOP-QKGSV6O\\Pictures");
        String rasshir = ".docx";
        File[] files = fromDirectory.listFiles((dir, name) -> name.endsWith(rasshir));
        if (files != null) {
            for (File file : files) {
                Path fromPath = inDirectory.toPath().resolve(file.getName());
                try {
                    Files.copy(file.toPath(), fromPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Файл " + file.getName() + " скопирован");
                } catch (IOException e) {
                    System.out.println("Не удалось скопировать файл " + file.getName());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Файлы с расширением " + rasshir + " не найдены");
        }
    }
}