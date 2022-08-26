package ru.netology.javachat.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final String filename;
    private final Boolean append;
    private final String nickname;
    private final String dataFormat;

    public Logger(String filename, Boolean append, String nickname, String dataFormat) {
        this.filename = filename;
        this.append = append;
        this.nickname = nickname;
        this.dataFormat = dataFormat;
    }


    public void logMsg(String text, boolean isError) {
        text = addNicknameAndTime(text, isError);
        printMsg(text, isError);
        saveMsg(text);
    }

    private String addNicknameAndTime(String text, boolean isError) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dataFormat);

        StringBuilder sb = new StringBuilder();
        if (isError) {
            sb.append("[ERROR] ");
        }
        sb.append("[")
                .append(nickname)
                .append("] [")
                .append(dtf.format(LocalDateTime.now()))
                .append("]: ")
                .append(text)
                .append("\n");
        return sb.toString();
    }

    private void saveMsg(String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) {
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
