package com.netology.javachat.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final String filename;
    private final String nickname;
    DateTimeFormatter dateTimeFormatter;
    FileWriter fileWriter;

    public Logger(String filename, String nickname, String dataFormat) {
        this.filename = filename;
        this.nickname = nickname;
        dateTimeFormatter = DateTimeFormatter.ofPattern(dataFormat);
    }

    public void logMsg(String text, boolean isError) {
        text = addNicknameAndTime(text, isError);
        printMsg(text, isError);
        saveMsg(text);
    }

    private String addNicknameAndTime(String text, boolean isError) {
        return (isError ? "[ERROR] " : "")
                + String.format("[%s] [%s]: %s%n", nickname, dateTimeFormatter.format(LocalDateTime.now()), text);
    }

    private void saveMsg(String text) {
        if (fileWriter == null) {
            try {
                fileWriter = new FileWriter(filename, true);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        try (BufferedWriter bw = new BufferedWriter(fileWriter)) {
            bw.write(text);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void printMsg(String text, boolean isError) {
        if (isError) {
            System.err.println(text);
        } else {
            System.out.print(text);
        }
    }
}
