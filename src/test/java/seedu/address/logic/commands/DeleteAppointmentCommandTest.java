package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteAppointmentCommandTest {

    private static final Nric VALID_NRIC = new PersonBuilder().build().getNric();
    private static final LocalDate VALID_DATE = LocalDate.of(2025, 10, 22);
    private static final LocalTime VALID_START_TIME = LocalTime.of(10, 0);
    private static final LocalTime VALID_END_TIME = LocalTime.of(11, 0);

    @Test
    public void execute_successfulDeletion() {

        Model modelStub = new ModelManager();
        Person testPerson = new PersonBuilder().build();
        modelStub.addPerson(testPerson);
        Appointment validAppointment = new Appointment(testPerson.getName().toString(), testPerson.getNric(),
                                LocalDateTime.of(VALID_DATE, VALID_START_TIME),
                                LocalDateTime.of(VALID_DATE, VALID_END_TIME));

        modelStub.addAppointment(validAppointment, testPerson);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(VALID_NRIC, VALID_DATE,
            VALID_START_TIME);
        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_SUCCESS, validAppointment);
        Model expectedModelStub = new ModelManager();
        Person expectedTestPerson = new PersonBuilder().build();
        expectedModelStub.addPerson(expectedTestPerson);
        expectedModelStub.addAppointment(validAppointment, expectedTestPerson);
        expectedModelStub.removeAppointment(validAppointment, expectedTestPerson);

        assertCommandSuccess(deleteAppointmentCommand, modelStub, expectedMessage, expectedModelStub);
    }

    @Test
    public void execute_invalidNric() {
        Model modelStub = new ModelManager();

        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(VALID_NRIC, VALID_DATE,
            VALID_START_TIME);
        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_PERSON_NOT_FOUND, VALID_NRIC);

        assertCommandFailure(deleteAppointmentCommand, modelStub, expectedMessage);
    }

}
