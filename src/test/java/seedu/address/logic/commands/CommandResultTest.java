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
import seedu.address.logic.commands.commandresult.ShowFilteredApptsCommandResult;
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
    public void equals_isShowPatientInfoCommandResultTest() {
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

        // different isShowPatientInfo value -> returns false
        assertFalse(commandResult.equals(new ShowPatientInfoCommandResult("feedback",
                KEANU, false)));
    }

    @Test
    public void equals_showFilteredApptsResultTest() {
        CommandResult commandResult = new ShowFilteredApptsCommandResult("feedback", true);

        //same values -> returns true
        assertTrue(commandResult.equals(new ShowFilteredApptsCommandResult("feedback", true)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new ShowFilteredApptsCommandResult("different", true)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new ShowFilteredApptsCommandResult("feedback", false)));
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
        CommandResult isShowPatientInfoCommandResult = new ShowPatientInfoCommandResult("feedback",
                KEANU, true);
        CommandResult isShowFilteredApptsCommandResult = new ShowFilteredApptsCommandResult("feedback", true);

        // same values -> returns same hashcode
        assertEquals(defaultCommandResult.hashCode(), new DefaultCommandResult("feedback").hashCode());
        assertEquals(exitCommandResult.hashCode(),
                new ExitCommandResult("feedback", true).hashCode());
        assertEquals(keywordCommandResult.hashCode(),
                new KeywordCommandResult("feedback", "add").hashCode());
        assertEquals(isShowPatientInfoCommandResult.hashCode(),
                new ShowPatientInfoCommandResult("feedback", KEANU, true).hashCode());
        assertEquals(isShowFilteredApptsCommandResult.hashCode(),
                new ShowFilteredApptsCommandResult("feedback", true).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(defaultCommandResult.hashCode(),
                new DefaultCommandResult("different").hashCode());
        assertNotEquals(exitCommandResult.hashCode(),
                new ExitCommandResult("different", true).hashCode());
        assertNotEquals(keywordCommandResult.hashCode(),
                new KeywordCommandResult("different", "add").hashCode());
        assertNotEquals(isShowPatientInfoCommandResult.hashCode(),
                new ShowPatientInfoCommandResult("different", KEANU, true).hashCode());
        assertNotEquals(isShowFilteredApptsCommandResult.hashCode(),
                new ShowFilteredApptsCommandResult("different", true).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(exitCommandResult.hashCode(),
                new ExitCommandResult("feedback", false).hashCode());

        // different keyword value -> returns different hashcode
        assertNotEquals(keywordCommandResult.hashCode(),
                new KeywordCommandResult("feedback", "delete").hashCode());

        // different patient value -> returns different hashcode
        assertNotEquals(isShowPatientInfoCommandResult.hashCode(),
                new ShowPatientInfoCommandResult("feedback", ALICE, true).hashCode());

        // different isShowPatientInfo value -> returns different hashcode
        assertNotEquals(isShowPatientInfoCommandResult.hashCode(),
                new ShowPatientInfoCommandResult("feedback", KEANU, false).hashCode());

        // different isShowFilteredAppts value -> returns different hashcode
        assertNotEquals(isShowFilteredApptsCommandResult.hashCode(),
                new ShowFilteredApptsCommandResult("feedback", false).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult defaultCommandResult = new DefaultCommandResult("feedback");
        String defaultCommandResultToString = DefaultCommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + defaultCommandResult.getFeedbackToUser()
                + ", isShowHelp=" + defaultCommandResult.isShowHelp()
                + ", isShowPatientInfo=" + defaultCommandResult.isShowPatientInfo()
                + ", isShowFilteredAppts=" + defaultCommandResult.isShowFilteredAppts()
                + ", keyword=" + defaultCommandResult.getKeyword()
                + ", patient=" + defaultCommandResult.getPatient()
                + ", isExit=" + defaultCommandResult.isExit() + "}";
        assertEquals(defaultCommandResultToString, defaultCommandResult.toString());

        CommandResult exitCommandResult = new ExitCommandResult("feedback", true);
        String exitCommandResultToString = ExitCommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + exitCommandResult.getFeedbackToUser()
                + ", isShowHelp=" + exitCommandResult.isShowHelp()
                + ", isShowPatientInfo=" + exitCommandResult.isShowPatientInfo()
                + ", isShowFilteredAppts=" + exitCommandResult.isShowFilteredAppts()
                + ", keyword=" + exitCommandResult.getKeyword()
                + ", patient=" + exitCommandResult.getPatient()
                + ", isExit=" + exitCommandResult.isExit() + "}";
        assertEquals(exitCommandResultToString, exitCommandResult.toString());

        CommandResult keywordCommandResult = new KeywordCommandResult("feedback", "add");
        String keywordCommandResultToString = KeywordCommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + keywordCommandResult.getFeedbackToUser()
                + ", isShowHelp=" + keywordCommandResult.isShowHelp()
                + ", isShowPatientInfo=" + keywordCommandResult.isShowPatientInfo()
                + ", isShowFilteredAppts=" + keywordCommandResult.isShowFilteredAppts()
                + ", keyword=" + keywordCommandResult.getKeyword()
                + ", patient=" + keywordCommandResult.getPatient()
                + ", isExit=" + keywordCommandResult.isExit() + "}";
        assertEquals(keywordCommandResultToString, keywordCommandResult.toString());

        CommandResult isShowPatientInfoCommandResult =
                new ShowPatientInfoCommandResult("feedback", KEANU, true);
        String addCommandResultToString = ShowPatientInfoCommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + isShowPatientInfoCommandResult.getFeedbackToUser()
                + ", isShowHelp=" + isShowPatientInfoCommandResult.isShowHelp()
                + ", isShowPatientInfo=" + isShowPatientInfoCommandResult.isShowPatientInfo()
                + ", isShowFilteredAppts=" + isShowPatientInfoCommandResult.isShowFilteredAppts()
                + ", keyword=" + isShowPatientInfoCommandResult.getKeyword()
                + ", patient=" + isShowPatientInfoCommandResult.getPatient()
                + ", isExit=" + isShowPatientInfoCommandResult.isExit() + "}";
        assertEquals(addCommandResultToString, isShowPatientInfoCommandResult.toString());

        CommandResult isShowFilteredApptsCommandResult =
                new ShowFilteredApptsCommandResult("feedback", true);
        String showFilteredApptsCommandResultToString = ShowFilteredApptsCommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + isShowFilteredApptsCommandResult.getFeedbackToUser()
                + ", isShowHelp=" + isShowFilteredApptsCommandResult.isShowHelp()
                + ", isShowPatientInfo=" + isShowFilteredApptsCommandResult.isShowPatientInfo()
                + ", isShowFilteredAppts=" + isShowFilteredApptsCommandResult.isShowFilteredAppts()
                + ", keyword=" + isShowFilteredApptsCommandResult.getKeyword()
                + ", patient=" + isShowFilteredApptsCommandResult.getPatient()
                + ", isExit=" + isShowFilteredApptsCommandResult.isExit() + "}";
        assertEquals(showFilteredApptsCommandResultToString, isShowFilteredApptsCommandResult.toString());
    }
}
