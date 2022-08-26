package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

class SocketHandler implements Runnable {
    private final Logger logger;
    private final ServerSettings serverSettings;

    private final Server server;
    private final Socket socket;
    private final PrintWriter out;
    private final Scanner in;

    private String nickName;

    public SocketHandler(Server server, Socket socket, Logger logger, ServerSettings serverSettings) throws IOException {
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
                    if (serverSettings.serverExitCommand.equals(data)) {
                        server.procedureDeleteUser(this);
                        break;
                    } else if (data.startsWith(serverSettings.serverNickNameCommand)) {
                        nickName = data.replace(serverSettings.serverNickNameCommand, "");
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

    public void sendMessage(String text) {
        out.println(text);
    }

    public String getNickName() {
        return nickName;
    }
}
