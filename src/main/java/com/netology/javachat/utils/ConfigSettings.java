package com.netology.javachat.utils;

public class ConfigSettings {
    private String serverHost;
    private int serverPort;
    private String serverLogFileName;
    private String clientLogFileName;
    private String serverLogDataFormat;
    private String clientLogDataFormat;
    private String serverLogNickname;
    private String clientLogNickname;
    private String serverCommandChangeNickname;
    private String serverCommandExit;

    public ConfigSettings() {
    }

    public String getServerHost() {
        return serverHost;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerLogFileName() {
        return serverLogFileName;
    }

    public String getClientLogFileName() {
        return clientLogFileName;
    }

    public String getServerLogDataFormat() {
        return serverLogDataFormat;
    }

    public String getClientLogDataFormat() {
        return clientLogDataFormat;
    }

    public String getServerLogNickname() {
        return serverLogNickname;
    }

    public String getClientLogNickname() {
        return clientLogNickname;
    }

    public String getServerCommandChangeNickname() {
        return serverCommandChangeNickname;
    }

    public String getServerCommandExit() {
        return serverCommandExit;
    }
}