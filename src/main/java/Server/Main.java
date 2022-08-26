package Server;

public class Main {
    public static final String SETTINGS_FILE = "Server.set";

    public static void main(String[] args) {
        ServerSettings serverSettings = YAMLSettings.readConfigFile(SETTINGS_FILE);
        Logger logger = new Logger(serverSettings);
        Server server = new Server(serverSettings, logger);
        server.start();
    }
}
