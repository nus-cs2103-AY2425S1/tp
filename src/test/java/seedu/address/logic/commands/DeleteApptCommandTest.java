package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.DeleteApptCommandParserTest;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.AppointmentExistsPredicate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains the integration tests (interaction with the Model) and unit tests for DeleteApptCommand.
 */

public class DeleteApptCommandTest {

    @Test
    public void execute_allFieldsSpecified_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person targetPerson = model.getFilteredPersonList().get(0);
        Nric nric = targetPerson.getNric();
        Appointment apptToDelete = targetPerson.getAppointments()
                                               .stream()
                                               .findFirst()
                                               .orElseThrow(() -> new AssertionError(
                                                       DeleteApptCommandParserTest.class
                                                       + ": PLEASE USE ANOTHER TARGET")
        );
        DeleteApptCommand delApptCommand = new DeleteApptCommand(
                new NricMatchesPredicate(nric),
                new AppointmentExistsPredicate(apptToDelete.getAppointmentDate(),
                                               apptToDelete.getAppointmentTimePeriod()));
        String[] newApptSet = targetPerson.getAppointments()
                                          .stream()
                                          .filter(x -> !x.equals(apptToDelete))
                                          .map(x -> x.getAppointmentName() + ":"
                                                    + x.getAppointmentDate() + ":"
                                                    + x.getAppointmentTimePeriod())
                                          .toArray(String[]::new);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person expectedPerson = new PersonBuilder().withNric(targetPerson.getNric().value)
                                                   .withName(targetPerson.getName().fullName)
                                                   .withAddress(targetPerson.getAddress().value)
                                                   .withEmail(targetPerson.getEmail().value)
                                                   .withGender(targetPerson.getGender().value)
                                                   .withPhone(targetPerson.getPhone().value)
                                                   .withDateOfBirth(targetPerson.getDateOfBirth().value)
                                                   .withTags(targetPerson.getTags()
                                                                         .stream()
                                                                         .map(x -> x.tagName)
                                                                         .toArray(String[]::new))
                                                   .withAppointments(newApptSet)
                                                   .build();
        expectedModel.setPerson(targetPerson, expectedPerson);
        String expectedMessage = String.format(DeleteApptCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS_2S,
                                               nric.value, apptToDelete.toString());
        assertCommandSuccess(delApptCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_patientNricNotFound_failure() {
        Nric nric = new Nric("S0000000Z");
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person targetPerson = model.getFilteredPersonList().get(0);
        Appointment apptToDelete = targetPerson.getAppointments()
                                               .stream()
                                               .findFirst()
                                               .orElseThrow(() -> new AssertionError(
                                                       DeleteApptCommandParserTest.class
                                                       + ": PLEASE USE ANOTHER TARGET")
        );
        NricMatchesPredicate nricNotFound = new NricMatchesPredicate(nric);
        AppointmentExistsPredicate validAppt = new AppointmentExistsPredicate(apptToDelete.getAppointmentDate(),
                                                                              apptToDelete.getAppointmentTimePeriod());
        DeleteApptCommand delApptPersonCommand = new DeleteApptCommand(nricNotFound, validAppt);
        assertCommandFailure(delApptPersonCommand, new ModelManager(getTypicalAddressBook(), new UserPrefs()),
                             Messages.MESSAGE_PERSON_NRIC_NOT_FOUND);
    }

    @Test
    public void execute_patientMissingAppointment_failure() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person targetPerson = model.getFilteredPersonList().get(0);
        Nric nric = targetPerson.getNric();
        Appointment apptToDelete = targetPerson.getAppointments()
                                               .stream()
                                               .findFirst()
                                               .orElseThrow(() -> new AssertionError(
                                                       DeleteApptCommandParserTest.class
                                                       + ": PLEASE USE ANOTHER TARGET")
        );
        DeleteApptCommand delApptCommandWrongDate = new DeleteApptCommand(
                new NricMatchesPredicate(nric),
                new AppointmentExistsPredicate("1900-01-01", apptToDelete.getAppointmentTimePeriod()));
        String expectedMessage = DeleteApptCommand.MESSAGE_PERSON_APPT_NOT_FOUND;
        assertCommandFailure(delApptCommandWrongDate, model, expectedMessage);

        DeleteApptCommand delApptCommandWrongTime = new DeleteApptCommand(
                new NricMatchesPredicate(nric),
                new AppointmentExistsPredicate(apptToDelete.getAppointmentDate(), "0000-1000"));
        String expectedMessage2 = DeleteApptCommand.MESSAGE_PERSON_APPT_NOT_FOUND;
        assertCommandFailure(delApptCommandWrongTime, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteApptCommand delApptCommand = new DeleteApptCommand(new NricMatchesPredicate(AMY.getNric()),
                new AppointmentExistsPredicate(VALID_APPOINTMENT_DATE_AMY, VALID_APPOINTMENT_TIMEPERIOD_AMY));
        DeleteApptCommand identicalCommand = new DeleteApptCommand(new NricMatchesPredicate(new Nric(VALID_NRIC_AMY)),
                                                                new AppointmentExistsPredicate(
                                                                        VALID_APPOINTMENT_DATE_AMY,
                                                                        VALID_APPOINTMENT_TIMEPERIOD_AMY));

        // noinspection EqualsWithItself
        assertEquals(delApptCommand, delApptCommand); // same obj
        assertEquals(delApptCommand, identicalCommand); // identical
        assertNotEquals(null, delApptCommand); // null

        // noinspection AssertBetweenInconvertibleTypes
        assertNotEquals(delApptCommand, new ClearCommand()); // different command
        assertNotEquals(delApptCommand, new DeleteApptCommand(new NricMatchesPredicate(BOB.getNric()),
                                                              new AppointmentExistsPredicate(
                                                                      VALID_APPOINTMENT_DATE_AMY,
                                                                      VALID_APPOINTMENT_TIMEPERIOD_AMY)));
        assertNotEquals(delApptCommand, new DeleteApptCommand(new NricMatchesPredicate(AMY.getNric()),
                                                              new AppointmentExistsPredicate(
                                                                      "1900-01-01",
                                                                      VALID_APPOINTMENT_TIMEPERIOD_AMY)));
        assertNotEquals(delApptCommand, new DeleteApptCommand(new NricMatchesPredicate(BOB.getNric()),
                                                              new AppointmentExistsPredicate(
                                                                      VALID_APPOINTMENT_DATE_AMY,
                                                                      "0000-2300")));
    }

    @Test
    public void toStringMethod() {
        DeleteApptCommand delApptCommand = new DeleteApptCommand(new NricMatchesPredicate(AMY.getNric()),
                                                                new AppointmentExistsPredicate(
                                                                        VALID_APPOINTMENT_DATE_AMY,
                                                                        VALID_APPOINTMENT_TIMEPERIOD_AMY));
        String expected = String.format("%s{targetNric=%s{nric=%s}, targetAppt=%s{date=%s, time period=%s}}",
                                        DeleteApptCommand.class.getCanonicalName(),
                                        NricMatchesPredicate.class.getCanonicalName(), ALICE.getNric(),
                                        AppointmentExistsPredicate.class.getCanonicalName(),
                                        VALID_APPOINTMENT_DATE_AMY, VALID_APPOINTMENT_TIMEPERIOD_AMY);
        assertEquals(expected, delApptCommand.toString());
    }

}
