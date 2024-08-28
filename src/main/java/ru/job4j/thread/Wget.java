package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String fileName;
    private final int speed;

    public Wget(String url, String fileName, int speed) {
        this.url = url;
        this.fileName = fileName;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            var file = new File(fileName);
            try (var input = new URL(url).openStream();
                 var output = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                long dataDownload = 0;
                long start = System.currentTimeMillis();
                while ((bytesRead = input.read(buffer, 0, buffer.length)) != -1) {
                    dataDownload += bytesRead;
                    if (dataDownload >= speed) {
                        long interval = System.currentTimeMillis() - start;
                        if (interval < 1000) {
                            Thread.sleep(1000 - interval);
                        }
                        dataDownload = 0;
                        start = System.currentTimeMillis();
                    }
                    output.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Неверно указаны параметры");
        }
        String url = args[0];
        String fileName = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, fileName, speed));
        wget.start();
        wget.join();
    }
}