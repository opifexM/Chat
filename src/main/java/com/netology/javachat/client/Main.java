package com.netology.javachat.client;


import com.netology.javachat.utils.ConfigSettings;
import com.netology.javachat.utils.Logger;
import com.netology.javachat.utils.YamlSettings;

public class Main {
    public static final String SETTINGS_FILE = "settings.txt";

    public static void main(String[] args) {
        ConfigSettings configSettings = YamlSettings.readConfigFile(SETTINGS_FILE);
        Logger logger = new Logger(configSettings.getClientLogFileName(),
                configSettings.getClientLogNickname(), configSettings.getClientLogDataFormat());
        Client client = new Client(configSettings, logger);
        client.start();
    }
}
