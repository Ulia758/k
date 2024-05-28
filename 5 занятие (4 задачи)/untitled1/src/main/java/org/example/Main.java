package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите путь к директории: ");
        String way = sc.nextLine();
        File directory = new File(way);
        if (!directory.isDirectory()) {
            System.out.println("Неправильный путь!");
            return;
        }
        System.out.print("Введите расширение файлов: ");
        String rasshir = sc.nextLine();
        List<File> filtrFile = filtr(directory, rasshir);
        System.out.println("Отфильтрованные файлы:");
        for (File file : filtrFile) {
            System.out.println(file.getName());
        }
    }
    private static List<File> filtr(File directory, String rasshir) {
        List<File> filteredFiles = new ArrayList<>();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(rasshir)) {
                filteredFiles.add(file);
            }
        }
        return filteredFiles;
    }
}