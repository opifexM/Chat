package com.netology.javachat.client;

import com.netology.javachat.utils.Logger;

import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Logger logger;
    private final Scanner socketIn;

    public ClientHandler(Scanner socketIn, Logger logger) {
        this.logger = logger;
        this.socketIn = socketIn;
    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        while (!current.isInterrupted()) {
            if (socketIn.hasNext()) {
                String serverAnswer = socketIn.nextLine();
                logger.logMessage(serverAnswer, false);
            }
        }
    }
}
