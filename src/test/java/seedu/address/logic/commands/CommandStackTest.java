package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStackTest {
    private CommandStack commandStack;

    @BeforeEach
    public void setUp() {
        commandStack = CommandStack.getInstance();
        commandStack.clear();
    }

    @Test
    public void pushCommand() {
        // EP: command is an UndoCommand
        Command command = new UndoCommand();
        commandStack.pushCommand(command);
        assertTrue(commandStack.isEmpty());

        // EP: command is not an UndoCommand
        command = new ListCommand();
        commandStack.pushCommand(command);
        assertEquals(commandStack.peek(), command);
        commandStack.clear();
    }

    @Test
    public void popCommand() {
        // EP: command stack is empty
        assertEquals(commandStack.popCommand(), null);

        // EP: command stack is not empty
        Command command = new ListCommand();
        commandStack.pushCommand(command);
        assertEquals(commandStack.popCommand(), command);
    }
}
