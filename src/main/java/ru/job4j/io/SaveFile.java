package ru.job4j.io;

import java.io.*;

public class SaveFile {

    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void contentSave(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            writer.write(content);
        }
    }
}
