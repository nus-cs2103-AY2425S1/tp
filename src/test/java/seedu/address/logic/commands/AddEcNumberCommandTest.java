package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EcNumber;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EmergencyPhoneCommand.
 */
public class AddEcNumberCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addEcNumberUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withEcNumber(VALID_ECNUMBER_AMY).build();
        AddEcNumberCommand addEcNumberCommand = new AddEcNumberCommand(INDEX_FIRST_PERSON,
                new EcNumber(editedPerson.getEcNumber().value));
        String expectedMessage = String.format(AddEcNumberCommand.MESSAGE_ADD_ECNUMBER_SUCCESS,
                Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(addEcNumberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteEcNumberUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withEcNumber("").build();
        AddEcNumberCommand addEcNumberCommand = new AddEcNumberCommand(INDEX_FIRST_PERSON,
                new EcNumber(editedPerson.getEcNumber().toString()));
        String expectedMessage = String.format(AddEcNumberCommand.MESSAGE_DELETE_ECNUMBER_SUCCESS,
                Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(addEcNumberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withEcNumber(VALID_ECNUMBER_AMY).build();
        AddEcNumberCommand addEcNumberCommand = new AddEcNumberCommand(INDEX_FIRST_PERSON,
                new EcNumber(editedPerson.getEcNumber().value));
        String expectedMessage = String.format(AddEcNumberCommand.MESSAGE_ADD_ECNUMBER_SUCCESS,
                Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(addEcNumberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddEcNumberCommand emergencyPhone = new AddEcNumberCommand(outOfBoundIndex,
                new EcNumber(VALID_ECNUMBER_BOB));
        assertCommandFailure(emergencyPhone, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        AddEcNumberCommand emergencyPhone = new AddEcNumberCommand(outOfBoundIndex,
                new EcNumber(VALID_ECNUMBER_BOB));


        assertCommandFailure(emergencyPhone, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddEcNumberCommand standardCommand = new AddEcNumberCommand(
                INDEX_FIRST_PERSON, new EcNumber(VALID_ECNUMBER_AMY));

        // same values -> returns true
        AddEcNumberCommand commandWithSameValues = new AddEcNumberCommand(
                INDEX_FIRST_PERSON, new EcNumber(VALID_ECNUMBER_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddEcNumberCommand(
                INDEX_SECOND_PERSON, new EcNumber(VALID_ECNUMBER_AMY))));

        // different emergencyPhone -> returns false
        assertFalse(standardCommand.equals(new AddEcNumberCommand(
                INDEX_FIRST_PERSON, new EcNumber(VALID_ECNUMBER_BOB))));

    }
}
