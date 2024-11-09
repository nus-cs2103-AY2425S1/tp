package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;

public class CommandResultTest {

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertEquals(commandResult, new CommandResult("feedback"));
        assertEquals(commandResult, new CommandResult("feedback", false, false, false));

        // same object -> returns true
        assertEquals(commandResult, commandResult);

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false)));

        // different requiresConfirmation value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, true)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertFalse(commandResult.hashCode() == new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertFalse(commandResult.hashCode() == new CommandResult("feedback", true, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertFalse(commandResult.hashCode() == new CommandResult("feedback", false, true, false).hashCode());

        // different requiresConfirmation value -> returns different hashcode
        assertFalse(commandResult.hashCode() == new CommandResult("feedback", false, false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback", true, false, true);
        String expected = new ToStringBuilder(commandResult)
                .add("feedbackToUser", "feedback")
                .add("showHelp", true)
                .add("exit", false)
                .add("requiresConfirmation", true)
                .toString();
        assertEquals(expected, commandResult.toString());
    }
}
