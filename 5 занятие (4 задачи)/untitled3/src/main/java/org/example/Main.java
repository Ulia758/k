package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String directory = "C:\\Users\\Student.DESKTOP-QKGSV6O\\Pictures";
        List<File> fileList = new ArrayList<>();
        getFile(new File(directory), fileList);
        sort(fileList);
        for (File file : fileList) {
            System.out.println("В файле " + file.getName() + " " + file.length() + " байт");
        }
    }
    public static void sort(List<File> fileList) {
        for (int i = 0; i < fileList.size()-1; i++) {
            for (int j = 0; j < fileList.size()-1-i; j++) {
                if (fileList.get(j).length() > fileList.get(j+1).length()) {
                    File temp = fileList.get(j);
                    fileList.set(j, fileList.get(j+1));
                    fileList.set(j+1, temp);
                }
            }
        }
    }

    public static void getFile(File directory, List<File> fileList) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getFile(file, fileList);
                } else {
                    fileList.add(file);
                }
            }
        }
    }
}