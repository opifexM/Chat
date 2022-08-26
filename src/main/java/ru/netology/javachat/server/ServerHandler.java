package ru.netology.javachat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

class ServerHandler implements Runnable {
    private final Logger logger;
    private final ServerSettings serverSettings;

    private final Server server;
    private final Socket socket;
    private final PrintWriter out;
    private final Scanner in;

    private String nickName;

    public ServerHandler(Server server, Socket socket, Logger logger, ServerSettings serverSettings) throws IOException {
        this.server = server;
        this.socket = socket;
        this.logger = logger;
        this.serverSettings = serverSettings;

        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (in.hasNext()) {
                    String data = in.nextLine();
                    if (serverSettings.getServerExitCommand().equals(data)) {
                        server.procedureDeleteUser(this);
                        break;
                    } else if (data.startsWith(serverSettings.getServerNickNameCommand())) {
                        nickName = data.replace(serverSettings.getServerNickNameCommand(), "");
                        server.procedureAddUser(this, nickName);
                    } else {
                        server.sendMessageToAll("[" + nickName + "] " + data);
                    }
                }
            }
            logger.logMsg("closing socket: " + socket, false);
        } catch (NoSuchElementException ex) {
            logger.logMsg("Connection is finished!", false);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.logMsg("Socket can't be closed!", false);
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
