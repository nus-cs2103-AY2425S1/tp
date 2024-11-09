package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.patient.Appt.FORMATTER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.DANIEL;
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

public class DeleteApptCommandTest {

    private Model model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());

    @Test
    public void execute_validDeleteAppt_success() {
        Patient patientToDeleteAppt = DANIEL;
        Appt validAppt = new Appt(LocalDateTime.parse("2030-06-06 22:00", FORMATTER), 
            new HealthService("Cancer Screening"));
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(patientToDeleteAppt.getNric(), 
            validAppt.getDateTime());
        String expectedMessage = String.format(DeleteApptCommand.MESSAGE_DELETE_APPT_SUCCESS, validAppt);

        CommandResult expectedCommandResult = new ShowPatientInfoCommandResult(expectedMessage, patientToDeleteAppt, true);
        ModelManager expectedModel = new ModelManager(model.getClinicConnectSystem(), new UserPrefs());

        assertCommandSuccess(deleteApptCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonExistentPatient_throwsCommandException() {
        Nric invalidNric = new Nric("S9999999A");
        Appt validAppt = new Appt(LocalDateTime.parse("2030-06-06 22:00", FORMATTER), 
            new HealthService("Cancer Screening"));
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(invalidNric, validAppt.getDateTime());
        assertThrows(CommandException.class, () -> deleteApptCommand.execute(model));
    }

    @Test 
    public void execute_nonExistentAppointment_throwsCommandException() {
        Patient patientToDeleteAppt = DANIEL;
        Appt nonExistentAppt = new Appt(LocalDateTime.parse("2023-01-28 13:00", FORMATTER), 
            new HealthService("CONSULT"));
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(patientToDeleteAppt.getNric(), 
            nonExistentAppt.getDateTime());
        assertThrows(CommandException.class, () -> deleteApptCommand.execute(model));
    }

    @Test
    public void equals() {
        Appt appt1 = new Appt(LocalDateTime.parse("2030-06-06 22:00", FORMATTER), 
            new HealthService("Cancer Screening"));
        Appt appt2 = new Appt(LocalDateTime.parse("2024-12-12 10:00", FORMATTER), 
            new HealthService("Vaccination"));
        DeleteApptCommand deleteApptCommand1 = new DeleteApptCommand(DANIEL.getNric(), appt1.getDateTime());
        DeleteApptCommand deleteApptCommand2 = new DeleteApptCommand(CARL.getNric(), appt2.getDateTime());

        // same object -> returns true
        assertTrue(deleteApptCommand1.equals(deleteApptCommand1));

        // different types -> returns false
        assertFalse(deleteApptCommand1.equals(1));

        // null -> returns false
        assertFalse(deleteApptCommand1.equals(null));

        // different appointment -> returns false
        assertFalse(deleteApptCommand1.equals(deleteApptCommand2));
    }

}
