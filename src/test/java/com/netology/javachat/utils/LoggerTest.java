package com.netology.javachat.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class LoggerTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void logMessage_succeeds() {
        Logger logger = new Logger("output.log", "testuser", "yyyy-MM-dd HH:mm:ss");
        logger.logMessage("Test message", false);
        String loggedMessage = outputStreamCaptor.toString().trim();
        Assert.assertTrue(loggedMessage.matches(
                "^\\[testuser\\]\\s\\[\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\]: Test message$"));
    }
}