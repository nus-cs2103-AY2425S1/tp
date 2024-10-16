package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandBufferTest {

    private CommandBuffer commandBuffer;

    @BeforeEach
    void setUp() {
        commandBuffer = new CommandBuffer();
    }

    @Test
    void addCommand_shouldAddNonEmptyCommand() {
        commandBuffer.addCommand("test command");
        assertEquals("test command", commandBuffer.handleUpInput());
    }

    @Test
    void addCommand_shouldNotAddBlankCommand() {
        commandBuffer.addCommand("");
        assertEquals("", commandBuffer.handleUpInput());

        commandBuffer.addCommand(" ");
        assertEquals("", commandBuffer.handleUpInput());
    }

    @Test
    void handleUpInput_shouldReturnPreviousCommand() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        assertEquals("first command", commandBuffer.handleUpInput());
        assertEquals("first command", commandBuffer.handleUpInput());
    }

    @Test
    void handleUpInput_shouldNotGoBeyondFirstCommand() {
        commandBuffer.addCommand("only command");

        assertEquals("only command", commandBuffer.handleUpInput());
        assertEquals("only command", commandBuffer.handleUpInput());
    }

    @Test
    void handleDownInput_shouldReturnNextCommand() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        commandBuffer.handleUpInput();
        assertEquals("second command", commandBuffer.handleDownInput());
    }

    @Test
    void handleDownInput_shouldNotGoBeyondLastCommand() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        assertEquals("second command", commandBuffer.handleDownInput());
        assertEquals("second command", commandBuffer.handleDownInput());
    }

    @Test
    void handleUpInput_shouldReturnEmptyForEmptyCommandHistory() {
        assertEquals("", commandBuffer.handleUpInput());
    }

    @Test
    void handleDownInput_shouldReturnEmptyForEmptyCommandHistory() {
        assertEquals("", commandBuffer.handleDownInput());
    }

    @Test
    void addCommand_shouldResetCommandPointer() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        commandBuffer.handleUpInput();
        commandBuffer.addCommand("third command");
        assertEquals("third command", commandBuffer.handleDownInput());
    }
}
