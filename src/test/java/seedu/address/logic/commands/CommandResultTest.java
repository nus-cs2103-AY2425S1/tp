package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.KEANU;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.DefaultCommandResult;
import seedu.address.logic.commands.commandresult.ExitCommandResult;
import seedu.address.logic.commands.commandresult.KeywordCommandResult;
import seedu.address.logic.commands.commandresult.ShowPatientInfoCommandResult;

public class CommandResultTest {
    @Test
    public void equals_defaultCommandResultTest() {
        CommandResult commandResult = new DefaultCommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new DefaultCommandResult("feedback")));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new DefaultCommandResult("different")));
    }

    @Test
    public void equals_keywordCommandResultTest() {
        CommandResult commandResult = new KeywordCommandResult("feedback", "add");

        // same values -> returns true
        assertTrue(commandResult.equals(new KeywordCommandResult("feedback", "add")));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new KeywordCommandResult("different", "add")));

        // different keyword value -> returns false
        assertFalse(commandResult.equals(new KeywordCommandResult("feedback", "delete")));
    }

    @Test
    public void equals_showPatientInfoCommandResultTest() {
        CommandResult commandResult = new ShowPatientInfoCommandResult("feedback", KEANU,
                true);

        // same values -> returns true
        assertTrue(commandResult.equals(new ShowPatientInfoCommandResult("feedback",
                KEANU, true)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new ShowPatientInfoCommandResult("different",
                KEANU, true)));

        // different patient value -> returns false
        assertFalse(commandResult.equals(new ShowPatientInfoCommandResult("feedback",
                ALICE, true)));

        // different showPatientInfo value -> returns false
        assertFalse(commandResult.equals(new ShowPatientInfoCommandResult("feedback",
                KEANU, false)));
    }

    @Test
    public void equals_exitCommandResultTest() {
        CommandResult commandResult = new ExitCommandResult("feedback", true);

        // same values -> returns true
        assertTrue(commandResult.equals(new ExitCommandResult("feedback", true)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new ExitCommandResult("different", true)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new ExitCommandResult("feedback", false)));
    }

    @Test
    public void hashcode() {
        CommandResult defaultCommandResult = new DefaultCommandResult("feedback");
        CommandResult exitCommandResult = new ExitCommandResult("feedback", true);
        CommandResult keywordCommandResult = new KeywordCommandResult("feedback", "add");
        CommandResult showPatientInfoCommandResult = new ShowPatientInfoCommandResult("feedback",
                KEANU, true);

        // same values -> returns same hashcode
        assertEquals(defaultCommandResult.hashCode(), new DefaultCommandResult("feedback").hashCode());
        assertEquals(exitCommandResult.hashCode(),
                new ExitCommandResult("feedback", true).hashCode());
        assertEquals(keywordCommandResult.hashCode(),
                new KeywordCommandResult("feedback", "add").hashCode());
        assertEquals(showPatientInfoCommandResult.hashCode(),
                new ShowPatientInfoCommandResult("feedback", KEANU, true).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(defaultCommandResult.hashCode(),
                new DefaultCommandResult("different").hashCode());
        assertNotEquals(exitCommandResult.hashCode(),
                new ExitCommandResult("different", true).hashCode());
        assertNotEquals(keywordCommandResult.hashCode(),
                new KeywordCommandResult("different", "add").hashCode());
        assertNotEquals(showPatientInfoCommandResult.hashCode(),
                new ShowPatientInfoCommandResult("different", KEANU, true).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(exitCommandResult.hashCode(),
                new ExitCommandResult("feedback", false).hashCode());

        // different keyword value -> returns different hashcode
        assertNotEquals(keywordCommandResult.hashCode(),
                new KeywordCommandResult("feedback", "delete").hashCode());

        // different patient value -> returns different hashcode
        assertNotEquals(showPatientInfoCommandResult.hashCode(),
                new ShowPatientInfoCommandResult("feedback", ALICE, true).hashCode());

        // different showPatientInfo value -> returns different hashcode
        assertNotEquals(showPatientInfoCommandResult.hashCode(),
                new ShowPatientInfoCommandResult("feedback", KEANU, false).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult defaultCommandResult = new DefaultCommandResult("feedback");
        String defaultCommandResultToString = DefaultCommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + defaultCommandResult.getFeedbackToUser()
                + ", showHelp=" + defaultCommandResult.isShowHelp()
                + ", showPatientInfo=" + defaultCommandResult.isShowPatientInfo()
                + ", keyword=" + defaultCommandResult.getKeyword()
                + ", patient=" + defaultCommandResult.getPatient()
                + ", exit=" + defaultCommandResult.isExit() + "}";
        assertEquals(defaultCommandResultToString, defaultCommandResult.toString());

        CommandResult exitCommandResult = new ExitCommandResult("feedback", true);
        String exitCommandResultToString = ExitCommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + exitCommandResult.getFeedbackToUser()
                + ", showHelp=" + exitCommandResult.isShowHelp()
                + ", showPatientInfo=" + exitCommandResult.isShowPatientInfo()
                + ", keyword=" + exitCommandResult.getKeyword()
                + ", patient=" + exitCommandResult.getPatient()
                + ", exit=" + exitCommandResult.isExit() + "}";
        assertEquals(exitCommandResultToString, exitCommandResult.toString());

        CommandResult keywordCommandResult = new KeywordCommandResult("feedback", "add");
        String keywordCommandResultToString = KeywordCommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + keywordCommandResult.getFeedbackToUser()
                + ", showHelp=" + keywordCommandResult.isShowHelp()
                + ", showPatientInfo=" + keywordCommandResult.isShowPatientInfo()
                + ", keyword=" + keywordCommandResult.getKeyword()
                + ", patient=" + keywordCommandResult.getPatient()
                + ", exit=" + keywordCommandResult.isExit() + "}";
        assertEquals(keywordCommandResultToString, keywordCommandResult.toString());

        CommandResult showPatientInfoCommandResult =
                new ShowPatientInfoCommandResult("feedback", KEANU, true);
        String addCommandResultToString = ShowPatientInfoCommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + showPatientInfoCommandResult.getFeedbackToUser()
                + ", showHelp=" + showPatientInfoCommandResult.isShowHelp()
                + ", showPatientInfo=" + showPatientInfoCommandResult.isShowPatientInfo()
                + ", keyword=" + showPatientInfoCommandResult.getKeyword()
                + ", patient=" + showPatientInfoCommandResult.getPatient()
                + ", exit=" + showPatientInfoCommandResult.isExit() + "}";
        assertEquals(addCommandResultToString, showPatientInfoCommandResult.toString());
    }
}
