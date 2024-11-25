package seedu.hireme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.hireme.model.internshipapplication.Status;

public class CommandResultTest {
    @Test
    public void nullFeedbackToUser_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandResult(null, false, false, false, null));
    }

    @Test
    public void isShowHelp() {
        CommandResult showHelpCommandResult = new CommandResult("feedback", true, false, false, null);
        CommandResult notShowHelpCommandResult = new CommandResult("feedback",
                                                            false, false, false, null);
        assertTrue(showHelpCommandResult.isShowHelp());
        assertFalse(notShowHelpCommandResult.isShowHelp());
    }

    @Test
    public void isExit() {
        CommandResult exitCommandResult = new CommandResult("feedback", false, true, false, null);
        CommandResult notExitCommandResult = new CommandResult("feedback",
                false, false, false, null);
        assertTrue(exitCommandResult.isExit());
        assertFalse(notExitCommandResult.isExit());
    }

    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");
        Map<Status, Integer> chartData = Map.ofEntries(Map.entry(Status.PENDING, 1),
                Map.entry(Status.ACCEPTED, 1),
                Map.entry(Status.REJECTED, 1));

        CommandResult commandResultWithChartData = new CommandResult("feedback", false, false, true,
                Map.ofEntries(Map.entry(Status.PENDING, 1),
                Map.entry(Status.ACCEPTED, 1),
                Map.entry(Status.REJECTED, 1)));

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, null)));
        assertTrue(commandResultWithChartData.equals(new CommandResult("feedback", false, false, true, chartData)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false, null)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false, null)));

        // different isChart value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, true, null)));

        // different chartData value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, false, false, chartData)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                        new CommandResult("feedback", true, false, false, null).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(),
                        new CommandResult("feedback", false, true, false, null).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", chart=" + commandResult.isChart()
                + ", chartData=" + commandResult.getChartData() + "}";
        assertEquals(expected, commandResult.toString());
    }
}
