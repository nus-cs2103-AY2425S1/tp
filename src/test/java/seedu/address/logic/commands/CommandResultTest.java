package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.log.AppointmentDate;
import seedu.address.model.person.IdentityNumber;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // Same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false, false, -1,
                false, null, null, null)));

        // Same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // Null -> returns false
        assertFalse(commandResult.equals(null));

        // Different type -> returns false
        assertFalse(commandResult.equals(0.5f));

        // Different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "different", false, false, false, false, -1,
                false, null, null, null)));

        // Different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", true, false, false, false, -1,
                false, null, null, null)));

        // Different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", false, true, false, false, -1,
                false, null, null, null)));

        // Different list value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", false, false, false, true, -1,
                false, null, null, null)));

        // Different personIndex value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", false, false, false, false, 0,
                false, null, null, null)));

        // Different isPopup value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", false, false, false, false, -1,
                true, null, null, null)));

        // Different identityNumber value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", false, false, false, false, -1,
                false, new IdentityNumber("S0000001I"), null, null)));

        // Different appointmentDate value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", false, false, false, false, -1,
                false, null, new AppointmentDate("01 JAN 2024"), null)));

        // Different logEntry value -> returns false
        assertFalse(commandResult.equals(new CommandResult(
                "feedback", false, false, false, false, -1,
                false, null, null, "Different log entry")));
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
        CommandResult commandResult = new CommandResult(
                "feedback",
                true,
                false,
                false,
                false,
                1,
                true,
                new IdentityNumber("S0000001I"),
                new AppointmentDate("01 JAN 2024"),
                "Entry log"
        );

        String expected = CommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + commandResult.getFeedbackToUser()
                + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit()
                + ", prompt=" + commandResult.hasPrompt()
                + ", list=" + commandResult.isList()
                + ", personIndex=" + commandResult.getPersonIndex()
                + ", isPopup=" + commandResult.isPopup()
                + ", identityNumber=" + commandResult.getIdentityNumber()
                + ", appointmentDate=" + commandResult.getAppointmentDate()
                + ", logEntry=" + commandResult.getLogEntry()
                + "}";

        assertEquals(expected, commandResult.toString());
    }

}
