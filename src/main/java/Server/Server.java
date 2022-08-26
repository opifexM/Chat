package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

public class Server {
    private final Logger logger;
    private final ServerSettings serverSettings;
    private final ConcurrentSkipListMap<SocketHandler, String> clientSockets = new ConcurrentSkipListMap<>((Comparator<SocketHandler>) Comparator.comparing((SocketHandler o) -> o.getNickName()));

    public Server(ServerSettings serverSettings, Logger logger) {
        this.serverSettings = serverSettings;
        this.logger = logger;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(serverSettings.serverPort)) {
            logger.logMsg("Server Started", false);

            while (true) {
                Socket socket = server.accept();
                SocketHandler socketHandler = new SocketHandler(this, socket, logger, serverSettings);
                new Thread(socketHandler).start();
                logger.logMsg("New socket: " + socket, false);
            }
        } catch (IOException e) {
            logger.logMsg("Server can't start. Reason: " + e.getMessage(), true);
        }
    }

    public void sendMessageToAll(String text) {
        logger.logMsg(text, false);
        Set<SocketHandler> keys = clientSockets.keySet();
        keys.forEach(k -> k.sendMessage(text));
    }

    public String listAllUsers() {
        Collection<String> users = clientSockets.values();
        return users.toString();
    }

    public void procedureAddUser(SocketHandler socketHandler, String nickname) {
        addUserSocket(socketHandler, nickname);
        sendMessageToAll("[CHAT] User '" + nickname + "' joined. Total users: " + getNumberOfClients() + ". Nicknames: " + listAllUsers());
    }

    public void procedureDeleteUser(SocketHandler socketHandler) {
        deleteUserSocket(socketHandler);
        String nickname = socketHandler.getNickName();
        sendMessageToAll("[CHAT] User '" + nickname + "' left. Total users: " + getNumberOfClients() + ". Nicknames: " + listAllUsers());
    }

    public void addUserSocket(SocketHandler socketHandler, String nickname) {
        clientSockets.put(socketHandler, nickname);
    }

    public void deleteUserSocket(SocketHandler socketHandler) {
        clientSockets.remove(socketHandler);
    }

    public int getNumberOfClients() {
        return clientSockets.size();
    }
}

