package ru.netology.javachat.server;

public class ServerSettings {
    private int serverPort;
    private String serverLogFileName;
    private boolean serverLogFileAppend;
    private String serverLogDataFormat;
    private String serverLogNickname;
    private String serverExitCommand;
    private String serverNickNameCommand;

    public ServerSettings() {
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerLogFileName() {
        return serverLogFileName;
    }

    public boolean isServerLogFileAppend() {
        return serverLogFileAppend;
    }

    public String getServerLogDataFormat() {
        return serverLogDataFormat;
    }

    public String getServerLogNickname() {
        return serverLogNickname;
    }

    public String getServerExitCommand() {
        return serverExitCommand;
    }

    public String getServerNickNameCommand() {
        return serverNickNameCommand;
    }
}

