package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Status;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UpdateAppointmentStatusCommandTest {

    private static final Nric VALID_NRIC = new PersonBuilder().build().getNric();
    private static final LocalDate VALID_DATE = LocalDate.of(2025, 10, 22);
    private static final LocalTime VALID_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime VALID_END_TIME = LocalTime.of(11, 0);
    private static final Status VALID_STATUS_COMPLETED = Status.COMPLETED;
    private static final Status VALID_STATUS_PENDING = Status.PENDING;

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateAppointmentStatusCommand(null, null, null, null));
    }

    @Test
    public void execute_validInput_success() {
        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().build();
        modelStub.addPerson(testPerson);
        Appointment validAppointment = new Appointment(testPerson.getName().toString(), testPerson.getNric(),
                                LocalDateTime.of(VALID_DATE, VALID_START_TIME),
                                LocalDateTime.of(VALID_DATE, VALID_END_TIME));

        modelStub.addAppointment(validAppointment, testPerson);
        UpdateAppointmentStatusCommand updateAppointmentStatusCommand = new UpdateAppointmentStatusCommand(
                VALID_NRIC, VALID_DATE, VALID_START_TIME, VALID_STATUS_COMPLETED);
        String expectedMessage = String.format(UpdateAppointmentStatusCommand.MESSAGE_SUCCESS, VALID_STATUS_COMPLETED);
        Model expectedModelStub = new ModelManager();
        Person expectedTestPerson = new PersonBuilder().build();
        expectedModelStub.addPerson(expectedTestPerson);
        expectedModelStub.addAppointment(validAppointment, expectedTestPerson);
        expectedModelStub.updateAppointmentStatus(validAppointment, VALID_STATUS_COMPLETED);
        assertCommandSuccess(updateAppointmentStatusCommand, modelStub, expectedMessage, expectedModelStub);
    }

    @Test
    public void execute_invalidNric() {
        Model modelStub = new ModelManager();

        UpdateAppointmentStatusCommand updateAppointmentStatusCommand = new UpdateAppointmentStatusCommand(
            VALID_NRIC, VALID_DATE, VALID_START_TIME, VALID_STATUS_PENDING);
        String expectedMessage = String.format(UpdateAppointmentStatusCommand.MESSAGE_PERSON_NOT_FOUND, VALID_NRIC);

        assertCommandFailure(updateAppointmentStatusCommand, modelStub, expectedMessage);
    }

    @Test
    public void execute_invalidAppointment() {
        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().build();
        modelStub.addPerson(testPerson);
        UpdateAppointmentStatusCommand updateAppointmentStatusCommand = new UpdateAppointmentStatusCommand(
            VALID_NRIC, VALID_DATE, VALID_START_TIME, VALID_STATUS_PENDING);
        String expectedMessage = String.format(UpdateAppointmentStatusCommand.MESSAGE_NO_APPOINTMENT);

        assertCommandFailure(updateAppointmentStatusCommand, modelStub, expectedMessage);
    }

    @Test
    public void equals() {
        UpdateAppointmentStatusCommand updateAppointmentStatusCommand1 = new UpdateAppointmentStatusCommand(VALID_NRIC,
            VALID_DATE, VALID_START_TIME, VALID_STATUS_PENDING);
        UpdateAppointmentStatusCommand updateAppointmentStatusCommand2 = new UpdateAppointmentStatusCommand(VALID_NRIC,
            VALID_DATE, VALID_START_TIME, VALID_STATUS_PENDING);

        // same object -> returns true
        assertTrue(updateAppointmentStatusCommand1.equals(updateAppointmentStatusCommand1));

        // same values -> returns true
        assertTrue(updateAppointmentStatusCommand1.equals(updateAppointmentStatusCommand2));

        // different types -> returns false
        assertFalse(updateAppointmentStatusCommand1.equals(1));

        // null -> returns false
        assertFalse(updateAppointmentStatusCommand1.equals(null));

        // different appointment -> returns false
        UpdateAppointmentStatusCommand updateAppointmentStatusCommand3 = new UpdateAppointmentStatusCommand(VALID_NRIC,
            VALID_DATE, LocalTime.of(12, 0), VALID_STATUS_PENDING);
        assertFalse(updateAppointmentStatusCommand1.equals(updateAppointmentStatusCommand3));
    }

}
