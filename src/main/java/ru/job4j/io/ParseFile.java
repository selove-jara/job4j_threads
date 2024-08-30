package ru.job4j.io;

import java.io.*;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return new InputFile(file).content(c -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return new InputFile(file).content(c -> c < 0x80);
    }

    public void saveContent(String content) throws IOException {
        new SaveFile(file).contentSave(content);
    }
}