package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.ui.Ui.UiState;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult1 = new CommandResult("feedback", UiState.DETAILS);

        // same values -> returns true
        assertTrue(commandResult1.equals(new CommandResult("feedback", UiState.DETAILS)));
        assertTrue(commandResult1.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult1.equals(commandResult1));

        // null -> returns false
        assertFalse(commandResult1.equals(null));

        // different types -> returns false
        assertFalse(commandResult1.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult1.equals(new CommandResult("different", UiState.DETAILS)));

        // different showHelp value -> returns false
        assertFalse(commandResult1.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult1.equals(new CommandResult("feedback", false, true)));

        // different uiState value -> returns false
        assertFalse(commandResult1.equals(new CommandResult("feedback", UiState.TASKS)));

        // different personToView value -> returns false
        CommandResult commandResult2 = new CommandResult("feedback", BOB);
        assertFalse(commandResult2.equals(new CommandResult("feedback", AMY)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult1 = new CommandResult("feedback", UiState.DETAILS);

        // same values -> returns same hashcode
        assertEquals(commandResult1.hashCode(), new CommandResult("feedback", UiState.DETAILS).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult1.hashCode(), new CommandResult("different",
                UiState.DETAILS).hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult1.hashCode(), new CommandResult("feedback",
                true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult1.hashCode(), new CommandResult("feedback",
                false, true).hashCode());

        // different uiState value -> returns different hashcode
        assertNotEquals(commandResult1.hashCode(),
                new CommandResult("feedback", UiState.TASKS).hashCode());

        // different personToView value -> returns different hashcode
        CommandResult commandResult2 = new CommandResult("feedback", BOB);
        assertNotEquals(commandResult2.hashCode(),
                new CommandResult("feedback", AMY).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback", UiState.DETAILS);
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", uiState=" + commandResult.getUiState()
                + ", personToView=" + commandResult.getPersonToView() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
