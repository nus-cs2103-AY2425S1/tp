package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandBufferTest {

    private static final boolean IS_VIEWING_HISTORY = true;
    private static final boolean IS_NOT_VIEWING_HISTORY = false;

    private CommandBuffer commandBuffer;

    @BeforeEach
    void setUp() {
        commandBuffer = new CommandBuffer();
    }

    @Test
    void addCommand_isViewingHistory_shouldAddNonEmptyCommand() {
        commandBuffer.addCommand("test command");
        assertEquals("test command", commandBuffer.handleUpInput(IS_VIEWING_HISTORY));
    }

    @Test
    void addCommand_isNotViewingHistory_shouldAddNonEmptyCommand() {
        commandBuffer.addCommand("test command");
        assertEquals("test command", commandBuffer.handleUpInput(IS_NOT_VIEWING_HISTORY));
    }

    @Test
    void addCommand_isViewingHistory_shouldNotAddBlankCommand() {
        commandBuffer.addCommand("");
        assertEquals("", commandBuffer.handleUpInput(IS_VIEWING_HISTORY));

        commandBuffer.addCommand(" ");
        assertEquals("", commandBuffer.handleUpInput(IS_VIEWING_HISTORY));
    }

    @Test
    void addCommand_isNotViewingHistory_shouldNotAddBlankCommand() {
        commandBuffer.addCommand("");
        assertEquals("", commandBuffer.handleUpInput(IS_NOT_VIEWING_HISTORY));

        commandBuffer.addCommand(" ");
        assertEquals("", commandBuffer.handleUpInput(IS_NOT_VIEWING_HISTORY));
    }

    @Test
    void handleUpInput_userViewingHistory_shouldReturnPreviousCommand() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        assertEquals("first command", commandBuffer.handleUpInput(IS_VIEWING_HISTORY));
    }

    @Test
    void handleUpInput_userNotViewingHistory_shouldReturnPreviousCommand() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        assertEquals("second command", commandBuffer.handleUpInput(IS_NOT_VIEWING_HISTORY));
    }

    @Test
    void handleUpInput_userViewingHistory_shouldNotGoBeyondFirstCommand() {
        commandBuffer.addCommand("only command");

        assertEquals("only command", commandBuffer.handleUpInput(IS_VIEWING_HISTORY));
        assertEquals("only command", commandBuffer.handleUpInput(IS_VIEWING_HISTORY));
    }

    @Test
    void handleUpInput_userNotViewingHistory_shouldNotGoBeyondFirstCommand() {
        commandBuffer.addCommand("only command");

        assertEquals("only command", commandBuffer.handleUpInput(IS_NOT_VIEWING_HISTORY));
        assertEquals("only command", commandBuffer.handleUpInput(IS_NOT_VIEWING_HISTORY));
    }

    @Test
    void handleDownInput_userViewingHistory_shouldReturnNextCommand() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        commandBuffer.handleUpInput(IS_VIEWING_HISTORY);
        assertEquals("second command", commandBuffer.handleDownInput());
    }

    @Test
    void handleDownInput_userNotViewingHistory_shouldReturnNextCommand() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        commandBuffer.handleUpInput(IS_NOT_VIEWING_HISTORY);
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
    void handleUpInput_userViewingHistory_shouldReturnEmptyForEmptyCommandHistory() {
        assertEquals("", commandBuffer.handleUpInput(IS_VIEWING_HISTORY));
    }

    @Test
    void handleUpInput_userNotViewingHistory_shouldReturnEmptyForEmptyCommandHistory() {
        assertEquals("", commandBuffer.handleUpInput(IS_NOT_VIEWING_HISTORY));
    }

    @Test
    void handleDownInput_shouldReturnEmptyForEmptyCommandHistory() {
        assertEquals("", commandBuffer.handleDownInput());
    }

    @Test
    void addCommand_userViewingHistory_shouldResetCommandPointer() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        commandBuffer.handleUpInput(IS_VIEWING_HISTORY);
        commandBuffer.addCommand("third command");
        assertEquals("third command", commandBuffer.handleDownInput());
    }

    @Test
    void addCommand_userNotViewingHistory_shouldResetCommandPointer() {
        commandBuffer.addCommand("first command");
        commandBuffer.addCommand("second command");

        commandBuffer.handleUpInput(IS_NOT_VIEWING_HISTORY);
        commandBuffer.addCommand("third command");
        assertEquals("third command", commandBuffer.handleDownInput());
    }
}
