package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class InputFile {

    private final File file;

    public InputFile(File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            int data;
            while ((data = reader.read()) != -1) {
                char c = (char) data;
                if (filter.test(c)) {
                    output.append(c);
                }
            }
        }
        return output.toString();
    }
}
