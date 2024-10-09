package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final String newApptName = "dental";
    private final String newApptDate = "2024-05-19";
    private final String newApptTimePeriod = "2000-2325";

    @Test
    public void execute_allFieldsSpecified_success() {
        Nric nric = model.getFilteredPersonList().get(0).getNric();
        AddApptCommand addApptCommand = new AddApptCommand(new NricMatchesPredicate(nric),
                                                           newApptName, newApptDate, newApptTimePeriod);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String expectedAppt = newApptName + ":" + newApptDate + ":" + newApptTimePeriod;
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
                                               newApptName, newApptDate, newApptTimePeriod);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), newApptPerson);
        assertCommandSuccess(addApptCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_patientNricNotFound_failure() {
        Nric nric = new Nric("S0000000Z");
        NricMatchesPredicate nricNotFound = new NricMatchesPredicate(nric);
        AddApptCommand addApptPersonCommand = new AddApptCommand(nricNotFound, newApptName, newApptDate,
                                                                 newApptTimePeriod);
        assertCommandFailure(addApptPersonCommand, model, Messages.MESSAGE_PERSON_NRIC_NOT_FOUND);
    }

    @Test
    public void equals() {
        AddApptCommand standardCommand = new AddApptCommand(new NricMatchesPredicate(AMY.getNric()),
                                                                  newApptName, newApptDate, newApptTimePeriod);
        AddApptCommand identicalCommand = new AddApptCommand(new NricMatchesPredicate(AMY.getNric()),
                                                                  newApptName, newApptDate, newApptTimePeriod);
        String anotherApptDate = "2024-12-23";
        assert !anotherApptDate.equals(newApptName);

        // noinspection EqualsWithItself
        assertEquals(standardCommand, standardCommand); // same obj
        assertEquals(standardCommand, identicalCommand); // identical
        assertNotEquals(null, standardCommand); // null

        // noinspection AssertBetweenInconvertibleTypes
        assertNotEquals(standardCommand, new ClearCommand()); // different command
        assertNotEquals(standardCommand, new AddApptCommand(new NricMatchesPredicate(BOB.getNric()), newApptName,
                                                            anotherApptDate, newApptTimePeriod));
        assertEquals(standardCommand, new AddApptCommand(new NricMatchesPredicate(AMY.getNric()), newApptName,
                                                            newApptDate, newApptTimePeriod));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        AddApptCommand addApptCommand = new AddApptCommand(new NricMatchesPredicate(ALICE.getNric()), newApptName,
                                                           newApptDate, newApptTimePeriod);
        String expected = String.format("%s{predicate=%s{nric=%s}, newApptName=%s, newApptDate=%s, newApptTime=%s}",
                                        AddApptCommand.class.getCanonicalName(),
                                        NricMatchesPredicate.class.getCanonicalName(), ALICE.getNric(), newApptName,
                                        newApptDate, newApptTimePeriod);
        assertEquals(expected, addApptCommand.toString());
    }

}
