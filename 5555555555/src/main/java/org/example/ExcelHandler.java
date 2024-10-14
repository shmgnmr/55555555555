package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class ExcelHandler {
    private static final String DESTINATION_PATH = "Results.txt";


    public ExcelHandler() throws IOException {
        ensureFileExists();
    }

    private void ensureFileExists() throws IOException {
        Path path = Paths.get(DESTINATION_PATH);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    public void writeToExcel(ArrayList<Result> results) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(DESTINATION_PATH))) {
            writer.write("№, Имя, Количество баллов");
            writer.newLine();
            for (int i = 0; i < results.size(); i++) {
                if (i < 10) {
                    writer.write((i + 1) + ", " + results.get(i).getName() + ", " + results.get(i).getPoints());
                    writer.newLine();
                }
            }
        }
    }

    public ArrayList<Result> readFromExcel() throws IOException {
        ArrayList<Result> results = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(DESTINATION_PATH))) {
            String line;
            reader.readLine(); // Пропустить заголовок
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(", ");
                if (values.length == 3) {
                    String name = values[1];
                    int points = Integer.parseInt(values[2]);
                    results.add(new Result(name, points));
                }
            }
        }
        return results;
    }
}