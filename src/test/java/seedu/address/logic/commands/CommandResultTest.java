package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));
    }

    @Test
    public void getUpdateCommandBox() {
        CommandResult commandResult = new CommandResult("feedback", false, false, true, false);
        assertTrue(commandResult.getUpdateCommandBox());

        commandResult = new CommandResult("feedback", false, false, false, false);
        assertFalse(commandResult.getUpdateCommandBox());
    }

    @Test
    public void getResultDisplay() {
        CommandResult commandResult = new CommandResult("feedback", false, false, false, true);
        assertTrue(commandResult.getResultDisplay());

        commandResult = new CommandResult("feedback", false, false, false, false);
        assertFalse(commandResult.getResultDisplay());
    }
}
