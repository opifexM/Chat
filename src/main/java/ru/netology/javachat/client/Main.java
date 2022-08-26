package ru.netology.javachat.client;


public class Main {
    public static final String SETTINGS_FILE = "Client.set";

    public static void main(String[] args) {
        ClientSettings clientSettings = YamlSettings.readConfigFile(SETTINGS_FILE);
        Logger logger = new Logger(clientSettings.getClientLogFileName(), clientSettings.isClientLogFileAppend(), clientSettings.getClientLogNickname(), clientSettings.getClientLogDataFormat());
        Client client = new Client(clientSettings, logger);
        client.start();
    }
}
