package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_3;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_4;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_DENTAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_DENTAL_AFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_DENTAL_BEF;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonBuilder;

/**
 * Contains the integration tests (interaction with the Model) and unit tests for AddApptCommand.
 */

public class AddApptCommandTest {

    @Test
    public void execute_allFieldsSpecified_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Person targetPerson = model.getFilteredPersonList().get(0);
        Nric nric = targetPerson.getNric();

        AddApptCommand addApptCommand = new AddApptCommand(new NricMatchesPredicate(nric),
                                                           VALID_APPOINTMENT_NAME_AMY,
                                                           VALID_APPOINTMENT_DATE_AMY,
                                                           VALID_APPOINTMENT_TIMEPERIOD_AMY);
        ArrayList<String> currentSerializedAppointmentLists =
                new ArrayList<>(targetPerson.getAppointments()
                                            .stream()
                                            .map(x -> x.getAppointmentName() + ":"
                                                      + x.getAppointmentDate() + ":"
                                                      + x.getAppointmentTimePeriod())
                                            .toList());
        String expectedAppt = VALID_APPOINTMENT_NAME_AMY + ":"
                              + VALID_APPOINTMENT_DATE_AMY + ":"
                              + VALID_APPOINTMENT_TIMEPERIOD_AMY;
        currentSerializedAppointmentLists.add(expectedAppt);
        Person newApptPerson = new PersonBuilder(targetPerson).withAppointments(
                currentSerializedAppointmentLists.toArray(String[]::new))
                                                              .build();
        String expectedMessage = String.format(AddApptCommand.MESSAGE_SUCCESS_4S, nric.value,
                                               VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                               VALID_APPOINTMENT_TIMEPERIOD_AMY);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), newApptPerson);
        assertCommandSuccess(addApptCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_consequtiveAppts_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Person targetPerson = model.getFilteredPersonList().get(0);
        Nric nric = targetPerson.getNric();

        ArrayList<String> currentSerializedAppointmentLists =
                new ArrayList<>(targetPerson.getAppointments()
                                            .stream()
                                            .map(x -> x.getAppointmentName() + ":"
                                                      + x.getAppointmentDate() + ":"
                                                      + x.getAppointmentTimePeriod())
                                            .toList());

        AddApptCommand addApptCommand = new AddApptCommand(new NricMatchesPredicate(nric),
                                                           VALID_APPOINTMENT_NAME_AMY,
                                                           VALID_APPOINTMENT_DATE_AMY,
                                                           VALID_APPOINTMENT_TIMEPERIOD_DENTAL);
        String expectedAppt = VALID_APPOINTMENT_NAME_AMY + ":"
                              + VALID_APPOINTMENT_DATE_AMY + ":"
                              + VALID_APPOINTMENT_TIMEPERIOD_DENTAL;
        currentSerializedAppointmentLists.add(expectedAppt);
        Person newApptPerson = new PersonBuilder(targetPerson).withAppointments(
                currentSerializedAppointmentLists.toArray(String[]::new))
                                                              .build();
        String expectedMessage = String.format(AddApptCommand.MESSAGE_SUCCESS_4S, nric.value,
                                               VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                               VALID_APPOINTMENT_TIMEPERIOD_DENTAL);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), newApptPerson);
        assertCommandSuccess(addApptCommand, model, expectedMessage, expectedModel);

        // Before
        addApptCommand = new AddApptCommand(new NricMatchesPredicate(nric),
                                            VALID_APPOINTMENT_NAME_AMY,
                                            VALID_APPOINTMENT_DATE_AMY,
                                            VALID_APPOINTMENT_TIMEPERIOD_DENTAL_BEF);
        expectedAppt = VALID_APPOINTMENT_NAME_AMY + ":"
                       + VALID_APPOINTMENT_DATE_AMY + ":"
                       + VALID_APPOINTMENT_TIMEPERIOD_DENTAL_BEF;
        currentSerializedAppointmentLists.add(expectedAppt);
        newApptPerson = new PersonBuilder(targetPerson).withAppointments(
                currentSerializedAppointmentLists.toArray(String[]::new))
                                                       .build();
        expectedMessage = String.format(AddApptCommand.MESSAGE_SUCCESS_4S, nric.value,
                                        VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                        VALID_APPOINTMENT_TIMEPERIOD_DENTAL_BEF);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), newApptPerson);
        assertCommandSuccess(addApptCommand, model, expectedMessage, expectedModel);

        // After
        addApptCommand = new AddApptCommand(new NricMatchesPredicate(nric),
                                            VALID_APPOINTMENT_NAME_AMY,
                                            VALID_APPOINTMENT_DATE_AMY,
                                            VALID_APPOINTMENT_TIMEPERIOD_DENTAL_AFT);
        expectedAppt = VALID_APPOINTMENT_NAME_AMY + ":"
                       + VALID_APPOINTMENT_DATE_AMY + ":"
                       + VALID_APPOINTMENT_TIMEPERIOD_DENTAL_AFT;
        currentSerializedAppointmentLists.add(expectedAppt);
        newApptPerson = new PersonBuilder(targetPerson).withAppointments(
                currentSerializedAppointmentLists.toArray(String[]::new))
                                                       .build();
        expectedMessage = String.format(AddApptCommand.MESSAGE_SUCCESS_4S, nric.value,
                                        VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                        VALID_APPOINTMENT_TIMEPERIOD_DENTAL_AFT);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), newApptPerson);
        assertCommandSuccess(addApptCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_patientNricNotFound_failure() {
        Nric nric = new Nric("S0000000Z");
        NricMatchesPredicate nricNotFound = new NricMatchesPredicate(nric);
        AddApptCommand addApptPersonCommand = new AddApptCommand(nricNotFound, VALID_APPOINTMENT_NAME_AMY,
                                                                 VALID_APPOINTMENT_DATE_AMY,
                                                                 VALID_APPOINTMENT_TIMEPERIOD_AMY);
        assertCommandFailure(addApptPersonCommand, new ModelManager(getTypicalAddressBook(), new UserPrefs()),
                             Messages.MESSAGE_PERSON_NRIC_NOT_FOUND);
    }

    @Test
    public void execute_patientDuplicateAppointments_failure() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Nric nric = new Nric(VALID_NRIC_AMY);
        NricMatchesPredicate nricAmy = new NricMatchesPredicate(nric);
        Person person = new PersonBuilder().withNric(VALID_NRIC_AMY)
                                           .withAppointments(VALID_APPOINTMENT_NAME_AMY
                                                             + ":" + VALID_APPOINTMENT_DATE_AMY
                                                             + ":" + VALID_APPOINTMENT_TIMEPERIOD_AMY)
                                           .build();
        model.addPerson(person);
        String expectedErrorMessage = String.format(AddApptCommand.MESSAGE_DUPLICATE_APPT_1S,
                                                    VALID_APPOINTMENT_NAME_AMY + " [ " + VALID_APPOINTMENT_DATE_AMY
                                                    + " @ " + VALID_APPOINTMENT_TIMEPERIOD_AMY + " ]");

        AddApptCommand addApptPersonCommand = new AddApptCommand(nricAmy, VALID_APPOINTMENT_NAME_AMY,
                                                                 VALID_APPOINTMENT_DATE_AMY,
                                                                 VALID_APPOINTMENT_TIMEPERIOD_AMY);
        assertCommandFailure(addApptPersonCommand, model, expectedErrorMessage);

        // different name, same time
        AddApptCommand addApptPersonCommand2 = new AddApptCommand(nricAmy, VALID_APPOINTMENT_NAME_BOB,
                                                                 VALID_APPOINTMENT_DATE_AMY,
                                                                 VALID_APPOINTMENT_TIMEPERIOD_AMY);
        assertCommandFailure(addApptPersonCommand2, model, expectedErrorMessage);
    }


    @Test
    public void execute_patientClashingAppointments_failure() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Nric nric = new Nric(VALID_NRIC_AMY);
        NricMatchesPredicate nricAmy = new NricMatchesPredicate(nric);
        Person person = new PersonBuilder().withNric(VALID_NRIC_AMY)
                                           .withAppointments(VALID_APPOINTMENT_NAME_AMY
                                                             + ":" + VALID_APPOINTMENT_DATE_AMY
                                                             + ":" + VALID_APPOINTMENT_TIMEPERIOD_AMY)
                                           .build();
        model.addPerson(person);
        String expectedErrorMessage = String.format(AddApptCommand.MESSAGE_CLASHING_APPT_1S,
                                                    VALID_APPOINTMENT_NAME_AMY + " [ " + VALID_APPOINTMENT_DATE_AMY
                                                    + " @ " + VALID_APPOINTMENT_TIMEPERIOD_AMY + " ]");

        AddApptCommand addApptPersonCommand = new AddApptCommand(nricAmy, VALID_APPOINTMENT_NAME_AMY,
                                                                 VALID_APPOINTMENT_DATE_AMY,
                                                                 INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_1);
        assertCommandFailure(addApptPersonCommand, model, expectedErrorMessage);

        AddApptCommand addApptPersonCommand2 = new AddApptCommand(nricAmy, VALID_APPOINTMENT_NAME_AMY,
                                                                  VALID_APPOINTMENT_DATE_AMY,
                                                                  INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_2);
        assertCommandFailure(addApptPersonCommand2, model, expectedErrorMessage);

        AddApptCommand addApptPersonCommand3 = new AddApptCommand(nricAmy, VALID_APPOINTMENT_NAME_AMY,
                                                                  VALID_APPOINTMENT_DATE_AMY,
                                                                  INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_3);
        assertCommandFailure(addApptPersonCommand3, model, expectedErrorMessage);

        AddApptCommand addApptPersonCommand4 = new AddApptCommand(nricAmy, VALID_APPOINTMENT_NAME_AMY,
                                                                  VALID_APPOINTMENT_DATE_AMY,
                                                                  INVALID_APPOINTMENT_TIMEPERIOD_CLASH_DENTAL_4);
        assertCommandFailure(addApptPersonCommand4, model, expectedErrorMessage);
    }

    @Test
    public void equals() {
        AddApptCommand standardCommand = new AddApptCommand(new NricMatchesPredicate(AMY.getNric()),
                                                            VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                                            VALID_APPOINTMENT_TIMEPERIOD_AMY);
        AddApptCommand identicalCommand = new AddApptCommand(new NricMatchesPredicate(AMY.getNric()),
                                                             VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                                             VALID_APPOINTMENT_TIMEPERIOD_AMY);
        String anotherApptDate = "2024-12-23";
        assert !anotherApptDate.equals(VALID_APPOINTMENT_NAME_AMY);

        // noinspection EqualsWithItself
        assertEquals(standardCommand, standardCommand); // same obj
        assertEquals(standardCommand, identicalCommand); // identical
        assertNotEquals(null, standardCommand); // null

        // noinspection AssertBetweenInconvertibleTypes
        assertNotEquals(standardCommand, new ClearCommand()); // different command
        assertNotEquals(standardCommand, new AddApptCommand(new NricMatchesPredicate(BOB.getNric()),
                                                            VALID_APPOINTMENT_NAME_AMY,
                                                            anotherApptDate, VALID_APPOINTMENT_TIMEPERIOD_AMY));
        assertEquals(standardCommand, new AddApptCommand(new NricMatchesPredicate(AMY.getNric()),
                                                         VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                                         VALID_APPOINTMENT_TIMEPERIOD_AMY));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        AddApptCommand addApptCommand = new AddApptCommand(new NricMatchesPredicate(ALICE.getNric()),
                                                           VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                                           VALID_APPOINTMENT_TIMEPERIOD_AMY);
        String expected = String.format("%s{predicate=%s{nric=%s}, newApptName=%s, newApptDate=%s, newApptTime=%s}",
                                        AddApptCommand.class.getCanonicalName(),
                                        NricMatchesPredicate.class.getCanonicalName(), ALICE.getNric(),
                                        VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                        VALID_APPOINTMENT_TIMEPERIOD_AMY);
        assertEquals(expected, addApptCommand.toString());
    }

}
