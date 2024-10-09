package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIMEPERIOD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddApptCommand.
 */

public class AddApptCommandTest {

    @Test
    public void execute_allFieldsSpecified_success() {
        Nric nric = ((Model) new ModelManager(getTypicalAddressBook(), new UserPrefs())).getFilteredPersonList().get(0).getNric();
        AddApptCommand addApptCommand = new AddApptCommand(new NricMatchesPredicate(nric), VALID_APPOINTMENT_NAME_AMY,
                                                           VALID_APPOINTMENT_DATE_AMY, VALID_APPOINTMENT_TIMEPERIOD_AMY);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedAppt = VALID_APPOINTMENT_NAME_AMY + ":" + VALID_APPOINTMENT_DATE_AMY + ":" + VALID_APPOINTMENT_TIMEPERIOD_AMY;
        Person newApptPerson = new PersonBuilder()
                .withNric(expectedModel.getFilteredPersonList().get(0).getNric().value)
                .withName(expectedModel.getFilteredPersonList().get(0).getName().fullName)
                .withAddress(expectedModel.getFilteredPersonList().get(0).getAddress().value)
                .withEmail(expectedModel.getFilteredPersonList().get(0).getEmail().value)
                .withGender(expectedModel.getFilteredPersonList().get(0).getGender().value)
                .withPhone(expectedModel.getFilteredPersonList().get(0).getPhone().value)
                .withDateOfBirth(expectedModel.getFilteredPersonList().get(0).getDateOfBirth().value)
                .withTags(expectedModel.getFilteredPersonList().get(0).getTags().stream().map(x -> x.tagName)
                                       .toArray(String[]::new))
                .withAppointments(expectedAppt)
                .build();
        String expectedMessage = String.format(AddApptCommand.MESSAGE_SUCCESS_4S, nric.value,
                                               VALID_APPOINTMENT_NAME_AMY, VALID_APPOINTMENT_DATE_AMY,
                                               VALID_APPOINTMENT_TIMEPERIOD_AMY);
        expectedModel.setPerson(
                ((Model) new ModelManager(getTypicalAddressBook(), new UserPrefs())).getFilteredPersonList().get(0), newApptPerson);
        assertCommandSuccess(addApptCommand, new ModelManager(getTypicalAddressBook(), new UserPrefs()), expectedMessage, expectedModel);
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
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Nric nric = new Nric(VALID_NRIC_AMY);
        NricMatchesPredicate nricAmy = new NricMatchesPredicate(nric);
        Person person = new PersonBuilder().withAppointments(VALID_APPOINTMENT_NAME_AMY,
                                                             VALID_APPOINTMENT_DATE_AMY,
                                                             VALID_APPOINTMENT_TIMEPERIOD_AMY)
                                           .withNric(VALID_NRIC_AMY)
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
