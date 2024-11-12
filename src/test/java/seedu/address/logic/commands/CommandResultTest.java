package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult.SwitchView;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false,
                SwitchView.NONE)));

        // same object -> returns true
        assertEquals(commandResult.isSwitchView(), new CommandResult("feedback", false, false,
                SwitchView.NONE).isSwitchView());

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertNotEquals(commandResult, new CommandResult("different"));

        // different showHelp value -> returns false
        assertNotEquals(commandResult, new CommandResult("feedback", true, false,
                SwitchView.NONE));

        // different exit value -> returns false
        assertNotEquals(commandResult, new CommandResult("feedback", false, true,
                SwitchView.NONE));

        // different switchView value -> returns false
        assertNotEquals(commandResult, new CommandResult("feedback", false, false,
                SwitchView.TASK));

        // different switchView value -> returns false
        assertNotEquals(commandResult, new CommandResult("feedback", false, false,
                SwitchView.WEDDING));

        assertFalse(commandResult.equals(new CommandResult("feedback", false, false,
                SwitchView.TASK)));

        // different switchView value -> returns false
        assertNotEquals(commandResult.isSwitchView(), new CommandResult("feedback", false, false,
                SwitchView.TASK).isSwitchView());
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, false,
                SwitchView.TASK).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", switchView=" + commandResult.getView() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
