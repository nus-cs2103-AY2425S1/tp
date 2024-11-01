package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.healthservice.HealthService;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Sex;
import seedu.address.testutil.PatientBuilder;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", null,
                false, null, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", null,
                true, null, false, false)));

        // different showPatientInfo value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", null,
                false, null, true, false)));

        // different patient value -> returns false
        Set<HealthService> healthServices = new HashSet<>();
        healthServices.add(new HealthService("Blood Test"));
        Patient newPatient = new Patient(new Name("Alice"), new Nric("S1111111A"),
                new Birthdate("2022-11-11"), new Sex("F"),
                new Phone("1111111"));
        assertFalse(commandResult.equals(new CommandResult("feedback", null,
                false, newPatient, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", null,
                false, null, false, true)));

        // different keyword value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", "keyword",
                false, null, false, false)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", null,
                true, null, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", null,
                false, null, false, true).hashCode());

        // different keyword value -> returns differnt hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", "keyword",
                false, null, false, false).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult standardCommandResult = new CommandResult("feedback");
        String standardToString = CommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + standardCommandResult.getFeedbackToUser()
                + ", showHelp=" + standardCommandResult.isShowHelp()
                + ", showPatientInfo=" + standardCommandResult.isShowPatientInfo()
                + ", keyword=" + standardCommandResult.getKeyword()
                + ", patient=" + standardCommandResult.getPatient()
                + ", exit=" + standardCommandResult.isExit() + "}";
        assertEquals(standardToString, standardCommandResult.toString());

        CommandResult helpCommandResult = new CommandResult("feedback", "addf");
        String helpCommandResultToString = CommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + helpCommandResult.getFeedbackToUser()
                + ", showHelp=" + helpCommandResult.isShowHelp()
                + ", showPatientInfo=" + helpCommandResult.isShowPatientInfo()
                + ", keyword=" + helpCommandResult.getKeyword()
                + ", patient=" + helpCommandResult.getPatient()
                + ", exit=" + helpCommandResult.isExit() + "}";
        assertEquals(helpCommandResultToString, helpCommandResult.toString());

        Patient standardPatient = new PatientBuilder().build();
        CommandResult addCommandResult = new CommandResult("feedback", standardPatient);
        String addCommandResultToString = CommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + addCommandResult.getFeedbackToUser()
                + ", showHelp=" + addCommandResult.isShowHelp()
                + ", showPatientInfo=" + addCommandResult.isShowPatientInfo()
                + ", keyword=" + addCommandResult.getKeyword()
                + ", patient=" + addCommandResult.getPatient()
                + ", exit=" + addCommandResult.isExit() + "}";
        assertEquals(addCommandResultToString, addCommandResult.toString());
    }
}
