package com.netology.javachat.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final String filename;
    private final String nickname;
    private final DateTimeFormatter dateTimeFormatter;
    private BufferedWriter bufferedWriter;

    public Logger(String filename, String nickname, String dataFormat) {
        this.filename = filename;
        this.nickname = nickname;
        dateTimeFormatter = DateTimeFormatter.ofPattern(dataFormat);
        initialFile();
    }

    private void initialFile() {
        try (FileWriter fileWriter = new FileWriter(filename, true)) {
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void saveMessage(String text) {
        try {
            bufferedWriter.write(text);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void logMessage(String text, boolean isError) {
        text = addNicknameAndTime(text, isError);
        printMessage(text, isError);
        saveMessage(text);
    }

    private String addNicknameAndTime(String text, boolean isError) {
        return (isError ? "[ERROR] " : "")
                + String.format("[%s] [%s]: %s%n", nickname, dateTimeFormatter.format(LocalDateTime.now()), text);
    }

    private void printMessage(String text, boolean isError) {
        if (isError) {
            System.err.println(text);
        } else {
            System.out.print(text);
        }
    }
}
