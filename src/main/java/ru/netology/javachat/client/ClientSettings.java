package ru.netology.javachat.client;

public class ClientSettings {
    public String serverHost;
    public int serverPort;
    public String clientLogFileName;
    public boolean clientLogFileAppend;
    public String clientLogDataFormat;
    public String clientLogNickname;
    public String clientExitCommand;
    public String serverNickNameCommand;

    public ClientSettings() {
    }

    public String getServerHost() {
        return serverHost;
    }
}


