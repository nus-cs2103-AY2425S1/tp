package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.patient.Appt.FORMATTER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.DANIEL;
import static seedu.address.testutil.TypicalPatients.FIONA;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ShowPatientInfoCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.patient.Appt;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

public class BookApptCommandTest {

    private Model model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());

    @Test
    public void execute_validAppointment_success() {
        Patient patientToBookAppt = DANIEL;
        Appt validAppt = new Appt(LocalDateTime.parse("2025-01-28 13:00", FORMATTER),
            new HealthService("CONSULT"));
        BookApptCommand bookApptCommand = new BookApptCommand(patientToBookAppt.getNric(), validAppt);
        String expectedMessage = String.format(BookApptCommand.MESSAGE_APPT_ADDED_SUCCESS,
            patientToBookAppt.getName());

        CommandResult expectedCommandResult = new ShowPatientInfoCommandResult(expectedMessage,
            patientToBookAppt, true);
        ModelManager expectedModel = new ModelManager(model.getClinicConnectSystem(), new UserPrefs());

        assertCommandSuccess(bookApptCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonExistentPatient_throwsCommandException() {
        Nric invalidNric = new Nric("S9999999A");
        Appt validAppt = new Appt(LocalDateTime.parse("2025-01-28 13:00", FORMATTER),
            new HealthService("CONSULT"));
        BookApptCommand bookApptCommand = new BookApptCommand(invalidNric, validAppt);
        assertThrows(CommandException.class, () -> bookApptCommand.execute(model));
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() {
        Patient patientToBookAppt = DANIEL;
        Appt duplicateAppt = new Appt(LocalDateTime.parse("2030-06-06 22:00", FORMATTER),
            new HealthService("CONSULT"));
        BookApptCommand bookApptCommand = new BookApptCommand(patientToBookAppt.getNric(), duplicateAppt);
        assertThrows(CommandException.class, () -> bookApptCommand.execute(model));
    }

    @Test
    public void execute_appointmentInPast_throwsCommandException() {
        Patient patientToBookAppt = DANIEL;
        Appt pastAppt = new Appt(LocalDateTime.parse("2020-09-09 22:00", FORMATTER),
            new HealthService("CONSULT"));
        BookApptCommand bookApptCommand = new BookApptCommand(patientToBookAppt.getNric(), pastAppt);
        assertThrows(CommandException.class, () -> bookApptCommand.execute(model));
    }

    @Test
    public void equals() {
        Appt appt1 = new Appt(LocalDateTime.parse("2025-01-28 13:00", FORMATTER),
            new HealthService("CONSULT"));
        Appt appt2 = new Appt(LocalDateTime.parse("2025-01-28 13:00", FORMATTER),
            new HealthService("CONSULT"));
        BookApptCommand bookApptCommand1 = new BookApptCommand(DANIEL.getNric(), appt1);
        BookApptCommand bookApptCommand2 = new BookApptCommand(FIONA.getNric(), appt2);

        // same object -> returns true
        assertTrue(bookApptCommand1.equals(bookApptCommand1));

        // different types -> returns false
        assertFalse(bookApptCommand1.equals(1));

        // null -> returns false
        assertFalse(bookApptCommand1.equals(null));

        // different patient -> returns false
        assertFalse(bookApptCommand1.equals(bookApptCommand2));
    }
}
