package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, false,
                -1, false, null, null, null)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false, false,
                -1, false, null, null, null)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false, false,
                -1, false, null, null, null)));

        // different list value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, false, true,
                -1, false, null, null, null)));

        //different personIndex value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, false, false,
                0, false, null, null, null)));;
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true,
                false, false, false, -1, false, null, null, null).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                true, false, false, -1, false, null, null, null).hashCode());

        // different list value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                false, false, true, -1, false, null, null, null).hashCode());

        // different personIndex value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                false, false, false, 0, false, null, null, null).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit()
                + ", prompt=" + commandResult.hasPrompt()
                + ", list=" + commandResult.isList()
                + ", personIndex=" + commandResult.getPersonIndex() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
