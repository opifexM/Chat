package ru.netology.javachat.client;


public class Main {
    public static final String SETTINGS_FILE = "Client.set";

    public static void main(String[] args) {
        ClientSettings clientSettings = YAMLSettings.readConfigFile(SETTINGS_FILE);
        Logger logger = new Logger(clientSettings.clientLogFileName, clientSettings.clientLogFileAppend, clientSettings.clientLogNickname, clientSettings.clientLogDataFormat);
        Client client = new Client(clientSettings, logger);
        client.start();
    }
}
