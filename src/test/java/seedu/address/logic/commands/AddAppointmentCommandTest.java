package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddAppointmentCommandTest {

    private static final Nric VALID_NRIC = new PersonBuilder().build().getNric();
    private static final LocalDate VALID_DATE = LocalDate.of(2025, 10, 22);
    private static final LocalTime VALID_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime VALID_END_TIME = LocalTime.of(11, 0);
    private static final LocalTime CONFLICTING_LOCAL_TIME = VALID_START_TIME.plusMinutes(1);


    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(null, null, null, null));
    }

    @Test
    public void execute_appointmentAcceptedByModel_addSuccessful() throws Exception {
        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().build();
        modelStub.addPerson(testPerson);
        Appointment validAppointment = new Appointment(testPerson.getName().toString(), testPerson.getNric(),
                                LocalDateTime.of(VALID_DATE, VALID_START_TIME),
                                LocalDateTime.of(VALID_DATE, VALID_END_TIME));

        CommandResult commandResult = new AddAppointmentCommand(VALID_NRIC, VALID_DATE,
            VALID_START_TIME, VALID_END_TIME)
                .execute(modelStub);

        assertEquals(String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAppointment), modelStub.getAllAppointments());
    }

    @Test
    public void execute_conflictingAppointment_throwsCommandException() throws Exception {
        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().build();
        modelStub.addPerson(testPerson);


        Appointment expected = new Appointment(testPerson.getName().toString(), testPerson.getNric(),
                LocalDateTime.of(VALID_DATE, VALID_START_TIME), LocalDateTime.of(VALID_DATE, VALID_END_TIME));

        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(VALID_NRIC,
            VALID_DATE, VALID_START_TIME, VALID_END_TIME);

        CommandResult expectedResult = new CommandResult(String.format(AddAppointmentCommand
            .MESSAGE_SUCCESS, expected));

        CommandResult result = addAppointmentCommand.execute(modelStub);


        assertEquals(result, expectedResult);


        AddAppointmentCommand conflictingAddAppointmentCommand = new AddAppointmentCommand(
            VALID_NRIC, VALID_DATE, CONFLICTING_LOCAL_TIME, VALID_END_TIME);




        assertThrows(CommandException.class, AddAppointmentCommand
            .MESSAGE_DUPLICATE_APPOINTMENT, () -> conflictingAddAppointmentCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidPerson_throwsCommandException() {
        Model modelStub = new ModelManager();
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(VALID_NRIC,
            VALID_DATE, VALID_START_TIME, VALID_END_TIME);

        assertThrows(CommandException.class, AddAppointmentCommand
            .MESSAGE_PERSON_NOT_FOUND, () -> addAppointmentCommand.execute(modelStub));
    }

    @Test
    public void execute_startTimeInPast_throwsCommandException() {
        Model modelStub = new ModelManager();
        LocalDate pastDate = LocalDate.of(2020, 10, 22);

        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(VALID_NRIC, pastDate,
            VALID_START_TIME, VALID_END_TIME);

        assertThrows(CommandException.class, AddAppointmentCommand
            .MESSAGE_START_TIME_IN_PAST, () -> addAppointmentCommand.execute(modelStub));
    }

    @Test
    public void execute_startTimeAfterEndTime_throwsCommandException() {
        Model modelStub = new ModelManager();
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(VALID_NRIC,
            VALID_DATE, VALID_END_TIME, VALID_START_TIME);

        assertThrows(CommandException.class, AddAppointmentCommand
            .MESSAGE_INVALID_START_END_TIME, () -> addAppointmentCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddAppointmentCommand addAppointmentCommand1 = new AddAppointmentCommand(VALID_NRIC,
            VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        AddAppointmentCommand addAppointmentCommand2 = new AddAppointmentCommand(VALID_NRIC,
            VALID_DATE, VALID_START_TIME, VALID_END_TIME);

        // same object -> returns true
        assertTrue(addAppointmentCommand1.equals(addAppointmentCommand1));

        // same values -> returns true
        assertTrue(addAppointmentCommand1.equals(addAppointmentCommand2));

        // different types -> returns false
        assertFalse(addAppointmentCommand1.equals(1));

        // null -> returns false
        assertFalse(addAppointmentCommand1.equals(null));

        // different appointment -> returns false
        AddAppointmentCommand addAppointmentCommand3 = new AddAppointmentCommand(VALID_NRIC,
            VALID_DATE, LocalTime.of(12, 0), LocalTime.of(13, 0));
        assertFalse(addAppointmentCommand1.equals(addAppointmentCommand3));
    }

    @Test
    public void toStringMethod() {
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(VALID_NRIC,
            VALID_DATE, VALID_START_TIME, VALID_END_TIME);
        String expected = AddAppointmentCommand.class.getCanonicalName()
            + "{toAdd=Appointment: " + VALID_NRIC
            + ", Date: " + VALID_DATE
            + ", Start: " + VALID_START_TIME
            + ", End: " + VALID_END_TIME + "}";
        assertEquals(expected, addAppointmentCommand.toString());
    }


}
