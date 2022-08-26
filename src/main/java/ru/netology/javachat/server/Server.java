package ru.netology.javachat.server;

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
    private final ConcurrentSkipListMap<ServerHandler, String> clientSockets = new ConcurrentSkipListMap<>((Comparator<ServerHandler>) Comparator.comparing((ServerHandler o) -> o.getNickName()));

    public Server(ServerSettings serverSettings, Logger logger) {
        this.serverSettings = serverSettings;
        this.logger = logger;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(serverSettings.serverPort)) {
            logger.logMsg("Server Started", false);

            while (true) {
                Socket socket = server.accept();
                ServerHandler serverHandler = new ServerHandler(this, socket, logger, serverSettings);
                new Thread(serverHandler).start();
                logger.logMsg("New socket: " + socket, false);
            }
        } catch (IOException e) {
            logger.logMsg("Server can't start. Reason: " + e.getMessage(), true);
        }
    }

    public void sendMessageToAll(String text) {
        logger.logMsg(text, false);
        Set<ServerHandler> keys = clientSockets.keySet();
        keys.forEach(k -> k.sendMessage(text));
    }

    public String listAllUsers() {
        Collection<String> users = clientSockets.values();
        return users.toString();
    }

    public void procedureAddUser(ServerHandler serverHandler, String nickname) {
        addUserSocket(serverHandler, nickname);
        sendMessageToAll("[CHAT] User '" + nickname + "' joined. Total users: " + getNumberOfClients() + ". Nicknames: " + listAllUsers());
    }

    public void procedureDeleteUser(ServerHandler serverHandler) {
        deleteUserSocket(serverHandler);
        String nickname = serverHandler.getNickName();
        sendMessageToAll("[CHAT] User '" + nickname + "' left. Total users: " + getNumberOfClients() + ". Nicknames: " + listAllUsers());
    }

    public void addUserSocket(ServerHandler serverHandler, String nickname) {
        clientSockets.put(serverHandler, nickname);
    }

    public void deleteUserSocket(ServerHandler serverHandler) {
        clientSockets.remove(serverHandler);
    }

    public int getNumberOfClients() {
        return clientSockets.size();
    }
}

