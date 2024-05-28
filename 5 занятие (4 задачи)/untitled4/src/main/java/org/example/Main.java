package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) {
        Path directory = Paths.get("C:\\Users\\Student.DESKTOP-QKGSV6O\\Pictures");
        long size = razmFiles(directory);
        System.out.println("Размер директории: " + size + " байт");
    }
    public static long razmFiles(Path directory) {
        try {
            DirectorySizeVisitor visitor = new DirectorySizeVisitor();
            Files.walkFileTree(directory, visitor);
            return visitor.getSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static class DirectorySizeVisitor extends SimpleFileVisitor<Path> {
        private long size = 0;

        public long getSize() {
            return size;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            size += Files.size(file);
            return super.visitFile(file, attrs);
        }
    }
}