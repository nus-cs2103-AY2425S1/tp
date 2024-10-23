package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;


public class CommandBoxTest {

    private static final String COMMAND_THAT_SUCCEEDS = "valid";
    private static final String COMMAND_THAT_FAILS = "invalid";

    private TestCommandBox commandBox;

    @BeforeEach
    public void setUp() {
        commandBox = new TestCommandBox(commandText -> {
            if (COMMAND_THAT_FAILS.equals(commandText)) {
                throw new CommandException("Command failed");
            }
            return new CommandResult("Command executed successfully");
        });
    }

    @Test
    public void constructor_nullCommandExecutor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TestCommandBox(null));
    }

    @Test
    public void handleCommandEntered_emptyString_noExecution() {
        commandBox.runCommand("");
        assertEquals("", commandBox.getCommandText());
        assertFalse(commandBox.isError());
    }

    @Test
    public void handleCommandEntered_validCommand_commandExecuted() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        assertEquals("", commandBox.getCommandText());
        assertFalse(commandBox.isError());
    }

    @Test
    public void handleCommandEntered_invalidCommand_errorStyleClassAdded() {
        commandBox.runCommand(COMMAND_THAT_FAILS);
        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandText());
        assertTrue(commandBox.isError());
    }

    @Test
    public void handleCommandEntered_subsequentValidCommand_errorStyleClassRemoved() {
        commandBox.runCommand(COMMAND_THAT_FAILS);
        assertTrue(commandBox.isError());

        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        assertFalse(commandBox.isError());
    }

    /**
     * A specialized version of {@code CommandBox} exposing only the methods that are needed for testing.
     */
    private class TestCommandBox {

        private String text = "";
        private List<String> styleClass = new ArrayList<>();
        private final CommandExecutor commandExecutor;

        TestCommandBox(CommandExecutor commandExecutor) {
            if (commandExecutor == null) {
                throw new NullPointerException("Command executor cannot be null");
            }
            this.commandExecutor = commandExecutor;
            styleClass.add("text-field");
        }

        void runCommand(String command) {
            setText(command);
            handleCommandEntered();
        }

        private void handleCommandEntered() {
            try {
                commandExecutor.execute(text);
                setText("");
                setStyleToDefault();
            } catch (CommandException | ParseException e) {
                setStyleToIndicateCommandFailure();
            }
        }

        String getCommandText() {
            return getText();
        }

        boolean isError() {
            return getStyleClass().contains("error");
        }

        private void setText(String text) {
            this.text = text;
        }

        private String getText() {
            return this.text;
        }

        private void setStyleToDefault() {
            styleClass.remove("error");
        }

        private void setStyleToIndicateCommandFailure() {
            if (!styleClass.contains("error")) {
                styleClass.add("error");
            }
        }

        private List<String> getStyleClass() {
            return styleClass;
        }
    }

    /**
     * A mock of the CommandExecutor interface for testing purposes.
     */
    @FunctionalInterface
    private interface CommandExecutor {
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }
}
