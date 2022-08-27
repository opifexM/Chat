package com.netology.javachat.server;

import com.netology.javachat.utils.ConfigSettings;
import com.netology.javachat.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

public class Server {
    private final Logger logger;
    private final ConfigSettings configSettings;
    private final ConcurrentSkipListMap<ServerHandler, String> clientSockets = new ConcurrentSkipListMap<>(Comparator.comparing(ServerHandler::getNickName));

    public Server(ConfigSettings configSettings, Logger logger) {
        this.configSettings = configSettings;
        this.logger = logger;
    }

    protected void start() {
        try (ServerSocket server = new ServerSocket(configSettings.getServerPort())) {
            logger.logMsg("Server Started", false);

            while (true) {
                Socket socket = server.accept();
                ServerHandler serverHandler = new ServerHandler(this, socket, logger, configSettings);
                new Thread(serverHandler).start();
                logger.logMsg("New socket: " + socket, false);
            }
        } catch (IOException e) {
            logger.logMsg("Server can't start. Reason: " + e.getMessage(), true);
        }
    }

    protected void sendMessageToAll(String text) {
        logger.logMsg(text, false);
        Set<ServerHandler> keys = clientSockets.keySet();
        keys.forEach(k -> k.sendMessage(text));
    }

    private String listAllUsers() {
        Collection<String> users = clientSockets.values();
        return users.toString();
    }

    protected void procedureAddUser(ServerHandler serverHandler, String nickname) {
        addUserSocket(serverHandler, nickname);
        sendMessageToAll("[CHAT] User '" + nickname + "' joined. Total users: "
                + getNumberOfClients() + ". Nicknames: " + listAllUsers());
    }

    protected void procedureDeleteUser(ServerHandler serverHandler) {
        deleteUserSocket(serverHandler);
        String nickname = serverHandler.getNickName();
        sendMessageToAll("[CHAT] User '" + nickname + "' left. Total users: "
                + getNumberOfClients() + ". Nicknames: " + listAllUsers());
    }

    private void addUserSocket(ServerHandler serverHandler, String nickname) {
        clientSockets.put(serverHandler, nickname);
    }

    private void deleteUserSocket(ServerHandler serverHandler) {
        clientSockets.remove(serverHandler);
    }

    private int getNumberOfClients() {
        return clientSockets.size();
    }
}

