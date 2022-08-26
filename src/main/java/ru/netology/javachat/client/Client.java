package ru.netology.javachat.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Logger logger;
    private final ClientSettings clientSettings;
    private String nickname;
    private Socket socket;
    private Scanner scanner;
    private Scanner socketIn;
    private PrintWriter socketOut;

    public Client(ClientSettings clientSettings, Logger logger) {
        this.clientSettings = clientSettings;
        this.logger = logger;

    }

    protected void start() {

        scanner = new Scanner(System.in);
        selectNickname();

        try {
            socket = new Socket(clientSettings.serverHost, clientSettings.serverPort);
            socketIn = new Scanner(socket.getInputStream());
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            logger.logMsg("Client Started. Socket connection: " + socket, false);
            logger.logMsg("Enter text for sending or '" + clientSettings.getClientExitCommand() + "' for finishing.", false);
            socketOut.println(clientSettings.getServerNickNameCommand() + nickname);

            ClientHandler clientHandler = new ClientHandler(socketIn, logger);
            Thread current = new Thread(clientHandler);
            current.start();

            boolean isFinished = false;
            while (!isFinished) {
                String userInput = scanner.nextLine();
                if (clientSettings.getClientExitCommand().equals(userInput)) {
                    isFinished = true;

                }
                socketOut.println(userInput);
            }

            logger.logMsg("[CHAT] Disconnected.", false);
            current.interrupt();
            socket.close();

        } catch (IOException e) {
            logger.logMsg("Creation of socket is failed! Host: " + clientSettings.serverHost + ", Port: " + clientSettings.serverPort, true);
        }
    }

    private void selectNickname() {
        while (nickname == null) {
            logger.logMsg("Enter the nickname (max 10 symbols): ", false);
            String text = scanner.nextLine();
            if (text.length() > 0 && text.length() < 10) {
                nickname = text;
            } else {
                logger.logMsg("Wrong nickname.", true);
            }
        }
    }
}
