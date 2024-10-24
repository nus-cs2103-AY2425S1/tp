package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_TIME_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_UNIQUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TIME_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TIME_APPOINTMENT_BOB;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class EditAppointmentCommandTest {
    private static final Nric VALID_NRIC = new PersonBuilder().build().getNric();
    private static final LocalDate VALID_DATE = LocalDate.of(2025, 10, 22);
    private static final LocalTime VALID_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime VALID_END_TIME = LocalTime.of(11, 0);
    private static final LocalDateTime startDateTime = LocalDateTime.of(2025, 10, 22, 10, 0);

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditAppointmentCommand(null, null, null));
    }

    @Test
    public void execute_appointmentAcceptedByModel_addSuccessful() throws Exception {
        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().build();
        modelStub.addPerson(testPerson);

        CommandResult commandResult = new AddAppointmentCommand(VALID_NRIC, VALID_DATE,
                VALID_START_TIME, VALID_END_TIME)
                .execute(modelStub);

        Appointment editedAppointment = new Appointment(VALID_NAME_BOB, new Nric(VALID_NRIC_BOB),
                VALID_START_DATE_TIME_APPOINTMENT_BOB, VALID_END_DATE_TIME_APPOINTMENT_BOB);

        CommandResult editCommandResult = new EditAppointmentCommand(VALID_NRIC,
                startDateTime,
                DESC_APPOINTMENT_BOB).execute(modelStub);

        assertEquals(String.format(EditAppointmentCommand.MESSAGE_SUCCESS, editedAppointment),
                editCommandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(editedAppointment), modelStub.getAllAppointments());
    }

    @Test
    public void equals() {

        final EditAppointmentCommand standardCommand = new EditAppointmentCommand(new Nric(VALID_NRIC_AMY),
                VALID_START_DATE_TIME_APPOINTMENT_AMY,
                DESC_APPOINTMENT_AMY);

        // same values -> returns true
        EditAppointmentDescriptor copyDescriptor = new EditAppointmentDescriptor(DESC_APPOINTMENT_AMY);
        EditAppointmentCommand commandWithSameValues = new EditAppointmentCommand(new Nric(VALID_NRIC_AMY),
                VALID_START_DATE_TIME_APPOINTMENT_AMY,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different nric -> returns false
        assertFalse(standardCommand.equals(new EditAppointmentCommand(new Nric(VALID_NRIC_BOB),
                VALID_START_DATE_TIME_APPOINTMENT_AMY, DESC_APPOINTMENT_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAppointmentCommand(new Nric(VALID_NRIC_AMY),
                VALID_START_DATE_TIME_APPOINTMENT_AMY, DESC_APPOINTMENT_BOB)));
    }

    @Test
    public void toStringMethod() {
        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(new Nric(VALID_NRIC_UNIQUE),
                VALID_START_DATE_TIME_APPOINTMENT_AMY,
                editAppointmentDescriptor);
        String expected = EditAppointmentCommand.class.getCanonicalName() + "{nric=" + VALID_NRIC_UNIQUE
                + ", editAppointmentDescriptor="
                + editAppointmentDescriptor + "}";
        assertEquals(expected, editAppointmentCommand.toString());
    }
}
