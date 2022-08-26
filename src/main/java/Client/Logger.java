package Client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final String filename;
    private final Boolean append;
    private final String nickname;
    public final String DATA_FORMAT;


    public Logger(ClientSettings clientSettings) {
        this.filename = clientSettings.clientLogFileName;
        this.append = clientSettings.clientLogFileAppend;
        this.nickname = clientSettings.clientLogNickname;
        DATA_FORMAT = clientSettings.clientLogDataFormat;
    }

    public void logMsg(String text, boolean isError) {
        text = addNicknameAndTime(text, isError);
        printMsg(text, isError);
        saveMsg(text);
    }

    public String addNicknameAndTime(String text, boolean isError) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATA_FORMAT);

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
    public void saveMsg(String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) {
            bw.write(text);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printMsg(String text, boolean isError) {
        if (isError) {
            System.err.println(text);
        } else {
            System.out.print(text);
        }
    }
}
