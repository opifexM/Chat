package Client;

public class ClientSettings implements Settings {
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


