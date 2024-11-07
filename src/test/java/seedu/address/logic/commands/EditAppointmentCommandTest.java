package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_TIME_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_UNIQUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TIME_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_TIME_APPOINTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.EditAppointmentCommand.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class EditAppointmentCommandTest {
    private static final LocalDate VALID_DATE = LocalDate.of(2025, 10, 22);
    private static final String VALID_DATE_STRING = "22/10/2025";
    private static final LocalTime VALID_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime VALID_END_TIME = LocalTime.of(11, 0);
    private static final LocalDateTime startDateTime = LocalDateTime.of(2025, 10, 22, 10, 0);
    private static final LocalTime CONFLICTING_LOCAL_TIME_HOUR_START = VALID_END_TIME.plusMinutes(1);
    private static final String CONFLICTING_LOCAL_TIME_HOUR_START_STRING = "11:01";
    private static final LocalTime CONFLICTING_LOCAL_TIME_HOUR_END = VALID_END_TIME.plusMinutes(60);
    private static final String CONFLICTING_LOCAL_TIME_HOUR_END_STRING = "12:00";

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditAppointmentCommand(null, null, null));
    }

    @Test
    public void execute_appointmentAcceptedByModel_editSuccessful() throws Exception {
        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB).build();
        modelStub.addPerson(testPerson);

        new AddAppointmentCommand(new Nric(VALID_NRIC_BOB), VALID_DATE,
                VALID_START_TIME, VALID_END_TIME)
                .execute(modelStub);

        Appointment editedAppointment = new Appointment(VALID_NAME_BOB, new Nric(VALID_NRIC_BOB),
                VALID_START_DATE_TIME_APPOINTMENT_BOB, VALID_END_DATE_TIME_APPOINTMENT_BOB);

        CommandResult editCommandResult = new EditAppointmentCommand(new Nric(VALID_NRIC_BOB),
                startDateTime,
                DESC_APPOINTMENT_BOB).execute(modelStub);

        assertEquals(String.format(EditAppointmentCommand.MESSAGE_SUCCESS, editedAppointment),
                editCommandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(editedAppointment), modelStub.getAllAppointments());
    }

    @Test
    public void execute_noAppointmentFoundByModel_editFailure() throws Exception {
        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB).build();
        modelStub.addPerson(testPerson);

        new AddAppointmentCommand(new Nric(VALID_NRIC_BOB), VALID_DATE,
                VALID_START_TIME, VALID_END_TIME)
                .execute(modelStub);

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptorBuilder()
                .build();

        EditAppointmentCommand editCommand = new EditAppointmentCommand(new Nric(VALID_NRIC_BOB),
                VALID_START_DATE_TIME_APPOINTMENT_AMY,
                editAppointmentDescriptor);
        assertCommandFailure(editCommand, modelStub, EditAppointmentCommand.MESSAGE_NO_APPOINTMENT);
    }

    @Test
    public void execute_appointmentStartTimeAfterEndTimeByModel_editFailure() throws Exception {
        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB).build();
        modelStub.addPerson(testPerson);

        new AddAppointmentCommand(new Nric(VALID_NRIC_BOB), VALID_DATE,
                VALID_START_TIME, VALID_END_TIME)
                .execute(modelStub);

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptorBuilder()
                .withStartTime(VALID_START_TIME_APPOINTMENT_AMY).withEndTime(VALID_END_TIME_APPOINTMENT_BOB)
                .build();

        EditAppointmentCommand editCommand = new EditAppointmentCommand(new Nric(VALID_NRIC_BOB),
                startDateTime,
                editAppointmentDescriptor);
        assertCommandFailure(editCommand, modelStub, EditAppointmentCommand.MESSAGE_INVALID_START_END_TIME);
    }

    @Test
    public void execute_patientNotFound_throwsCommandException() {
        Model modelStub = new ModelManager();
        Nric nonExistentNric = new Nric("S9876543C");
        EditAppointmentCommand.EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withDate("10/12/2025")
                .withStartTime("10:00")
                .withEndTime("11:00")
                .build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(nonExistentNric,
                startDateTime, descriptor);
        assertCommandFailure(editAppointmentCommand, modelStub, MESSAGE_PERSON_NOT_FOUND);
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

    @Test
    public void execute_conflictingAppointment_throwsCommandException() throws Exception {
        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().build();
        modelStub.addPerson(testPerson);


        Appointment expected = new Appointment(testPerson.getName().toString(), testPerson.getNric(),
                LocalDateTime.of(VALID_DATE, VALID_START_TIME), LocalDateTime.of(VALID_DATE, VALID_END_TIME));

        modelStub.addAppointment(expected, testPerson);

        Appointment newExpected = new Appointment(testPerson.getName().toString(), testPerson.getNric(),
                LocalDateTime.of(VALID_DATE, CONFLICTING_LOCAL_TIME_HOUR_START),
                LocalDateTime.of(VALID_DATE, CONFLICTING_LOCAL_TIME_HOUR_END));

        modelStub.addAppointment(newExpected, testPerson);

        Appointment newAppointment = new Appointment(testPerson.getName().toString(), testPerson.getNric(),
            LocalDateTime.of(VALID_DATE, CONFLICTING_LOCAL_TIME_HOUR_START),
            LocalDateTime.of(VALID_DATE, CONFLICTING_LOCAL_TIME_HOUR_END));

        assertTrue(modelStub.hasAppointment(newAppointment));

        EditAppointmentCommand.EditAppointmentDescriptor descAppointment = new EditAppointmentDescriptorBuilder()
                .withDate(VALID_DATE_STRING)
                .withStartTime(CONFLICTING_LOCAL_TIME_HOUR_START_STRING)
                .withEndTime(CONFLICTING_LOCAL_TIME_HOUR_END_STRING).build();

        EditAppointmentCommand editCommandResult = new EditAppointmentCommand(new Nric(testPerson.getNric().toString()),
                startDateTime,
                descAppointment);

        assertThrows(CommandException.class, EditAppointmentCommand
            .MESSAGE_DUPLICATE_APPOINTMENT, () -> editCommandResult.execute(modelStub));

    }

}
