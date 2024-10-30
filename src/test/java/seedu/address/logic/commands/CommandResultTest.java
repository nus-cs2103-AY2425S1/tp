package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    private static final CommandResult NORMAL_COMMAND_RESULT = new CommandResult("normal feedback");

    private static final CommandResult VIEW_COMMAND_RESULT = new CommandResult("view feedback",
            INDEX_SECOND_PERSON, false, false);

    private static final CommandResult HELP_COMMAND_RESULT = new CommandResult("help feedback",
            CommandResult.NO_INDEX_TO_VIEW, true, false);

    private static final CommandResult EXIT_COMMAND_RESULT = new CommandResult("exit feedback",
            CommandResult.NO_INDEX_TO_VIEW, false, true);

    @Test
    public void equals() {

        // same values -> returns true
        assertTrue(NORMAL_COMMAND_RESULT.equals(new CommandResult("normal feedback")));
        assertTrue(NORMAL_COMMAND_RESULT.equals(new CommandResult("normal feedback",
                CommandResult.NO_INDEX_TO_VIEW, false, false)));

        // same object -> returns true
        assertTrue(NORMAL_COMMAND_RESULT.equals(NORMAL_COMMAND_RESULT));

        // null -> returns false
        assertFalse(NORMAL_COMMAND_RESULT.equals(null));

        // different types -> returns false
        assertFalse(NORMAL_COMMAND_RESULT.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(NORMAL_COMMAND_RESULT.equals(new CommandResult("different feedback")));

        // different indexToView value -> returns false
        assertFalse(NORMAL_COMMAND_RESULT.equals(new CommandResult("normal feedback",
                INDEX_FIRST_PERSON, false, false)));

        // different showHelp value -> returns false
        assertFalse(NORMAL_COMMAND_RESULT.equals(new CommandResult("normal feedback",
                CommandResult.NO_INDEX_TO_VIEW, true, false)));

        // different exit value -> returns false
        assertFalse(NORMAL_COMMAND_RESULT.equals(new CommandResult("normal feedback",
                CommandResult.NO_INDEX_TO_VIEW, false, true)));
    }

    @Test
    public void isViewCommand() {
        assertFalse(NORMAL_COMMAND_RESULT.isViewCommand());
        assertTrue(VIEW_COMMAND_RESULT.isViewCommand());
        assertFalse(HELP_COMMAND_RESULT.isViewCommand());
        assertFalse(EXIT_COMMAND_RESULT.isViewCommand());
    }

    @Test
    public void isShowHelp() {
        assertFalse(NORMAL_COMMAND_RESULT.isShowHelp());
        assertFalse(VIEW_COMMAND_RESULT.isShowHelp());
        assertTrue(HELP_COMMAND_RESULT.isShowHelp());
        assertFalse(EXIT_COMMAND_RESULT.isShowHelp());
    }

    @Test
    public void isExit() {
        assertFalse(NORMAL_COMMAND_RESULT.isExit());
        assertFalse(VIEW_COMMAND_RESULT.isExit());
        assertFalse(HELP_COMMAND_RESULT.isExit());
        assertTrue(EXIT_COMMAND_RESULT.isExit());
    }

    @Test
    public void hashcode() {
        // same values -> returns same hashcode
        assertEquals(NORMAL_COMMAND_RESULT.hashCode(), new CommandResult("normal feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(NORMAL_COMMAND_RESULT.hashCode(), new CommandResult("different").hashCode());

        // different indexToView value -> returns different hashcode
        assertNotEquals(NORMAL_COMMAND_RESULT.hashCode(), new CommandResult("normal feedback",
                INDEX_FIRST_PERSON, false, false).hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(NORMAL_COMMAND_RESULT.hashCode(), new CommandResult("normal feedback",
                CommandResult.NO_INDEX_TO_VIEW, true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(NORMAL_COMMAND_RESULT.hashCode(), new CommandResult("normal feedback",
                CommandResult.NO_INDEX_TO_VIEW, false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = CommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + NORMAL_COMMAND_RESULT.getFeedbackToUser()
                + ", indexToView=" + NORMAL_COMMAND_RESULT.getIndexToView()
                + ", showHelp=" + NORMAL_COMMAND_RESULT.isShowHelp()
                + ", shouldExit=" + NORMAL_COMMAND_RESULT.isExit() + "}";
        assertEquals(expected, NORMAL_COMMAND_RESULT.toString());
    }
}
