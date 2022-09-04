package com.netology.javachat.server;

import com.netology.javachat.utils.ConfigSettings;
import com.netology.javachat.utils.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

class ServerHandler implements Runnable {
    private final Logger logger;
    private final ConfigSettings configSettings;

    private final Server server;
    private final Socket socket;
    private final PrintWriter out;
    private final Scanner in;

    private String nickName;

    public ServerHandler(Server server, Socket socket, Logger logger, ConfigSettings configSettings) throws IOException {
        this.server = server;
        this.socket = socket;
        this.logger = logger;
        this.configSettings = configSettings;

        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (in.hasNext()) {
                    String data = in.nextLine();
                    if (configSettings.getServerCommandExit().equals(data)) {
                        server.procedureDeleteUser(this);
                        break;
                    } else if (data.startsWith(configSettings.getServerCommandChangeNickname())) {
                        nickName = data.replace(configSettings.getServerCommandChangeNickname(), "");
                        server.procedureAddUser(this, nickName);
                    } else {
                        server.sendMessageToAll("[" + nickName + "] " + data);
                    }
                }
            }
            logger.logMessage("closing socket: " + socket, false);
        } catch (NoSuchElementException ex) {
            logger.logMessage("Connection is finished!", false);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.logMessage("Socket can't be closed!", false);
            }
        }
    }

    protected void sendMessage(String text) {
        out.println(text);
    }

    protected String getNickName() {
        return nickName;
    }
}
