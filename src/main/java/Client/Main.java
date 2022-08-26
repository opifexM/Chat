package Client;


import Client.Logger;

public class Main {
    public static final String SETTINGS_FILE = "Client.set";

    public static void main(String[] args) {
        ClientSettings clientSettings = YAMLSettings.readConfigFile(SETTINGS_FILE);
        Logger logger = new Logger(clientSettings);
        Client client = new Client(clientSettings, logger);
        client.start();
    }
}
