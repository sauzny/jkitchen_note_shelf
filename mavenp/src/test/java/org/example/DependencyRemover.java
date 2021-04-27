package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DependencyRemover {

    private static String rootPath = "F:\\m3";

    public static void moveToBak(String strPath, String sourceFileName) throws IOException {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                moveToBak(file.getAbsolutePath(), sourceFileName);
            } else {
                String fileName = file.getName();
                if (fileName.equals(sourceFileName)) {
                    Path sourcePath = Paths.get(file.getAbsolutePath());
                    Path targetPath = Paths.get(file.getParent(), fileName + ".bak");
                    Files.move(sourcePath, targetPath);
                }

            }
        }

    }

    public static void main(String[] args) throws IOException {

        DependencyRemover.moveToBak(rootPath, "_remote.repositories");

    }
}
