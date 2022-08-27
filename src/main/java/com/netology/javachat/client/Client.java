package com.netology.javachat.client;

import com.netology.javachat.utils.ConfigSettings;
import com.netology.javachat.utils.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Logger logger;
    private final ConfigSettings configSettings;
    private String nickname;
    private Scanner scanner;

    public Client(ConfigSettings configSettings, Logger logger) {
        this.configSettings = configSettings;
        this.logger = logger;

    }

    protected void start() {

        scanner = new Scanner(System.in);
        selectNickname();

        try (Socket socket = new Socket(configSettings.getServerHost(), configSettings.getServerPort());
             Scanner socketIn = new Scanner(socket.getInputStream())) {
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
            logger.logMsg("Client Started. Socket connection: " + socket, false);
            logger.logMsg("Enter text for sending or '" + configSettings.getServerCommandExit()
                    + "' for finishing.", false);
            socketOut.println(configSettings.getServerCommandChangeNickname() + nickname);

            ClientHandler clientHandler = new ClientHandler(socketIn, logger);
            Thread current = new Thread(clientHandler);
            current.start();

            boolean isFinished = false;
            while (!isFinished) {
                String userInput = scanner.nextLine();
                if (configSettings.getServerCommandExit().equals(userInput)) {
                    isFinished = true;

                }
                socketOut.println(userInput);
            }

            logger.logMsg("[CHAT] Disconnected.", false);
            current.interrupt();

        } catch (IOException e) {
            logger.logMsg("Creation of socket is failed! Host: "
                    + configSettings.getServerHost() + ", Port: " + configSettings.getServerPort(), true);
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
