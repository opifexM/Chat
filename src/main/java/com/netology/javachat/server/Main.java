package com.netology.javachat.server;

import com.netology.javachat.utils.ConfigSettings;
import com.netology.javachat.utils.Logger;
import com.netology.javachat.utils.YamlSettings;

public class Main {
    public static final String SETTINGS_FILE = "settings.txt";

    public static void main(String[] args) {
        ConfigSettings configSettings = YamlSettings.readConfigFile(SETTINGS_FILE);
        Logger logger = new Logger(configSettings.getServerLogFileName(),
                configSettings.getServerLogNickname(), configSettings.getServerLogDataFormat());
        Server server = new Server(configSettings, logger);
        server.start();
    }
}
