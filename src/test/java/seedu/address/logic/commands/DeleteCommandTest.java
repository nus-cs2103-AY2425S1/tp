package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private static final IdentityNumber IDENTITY_NUMBER_NOT_FOUND = new IdentityNumber("F2760624P");
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Test for successfully deleting a person by identity number.
     */
    @Test
    public void execute_validIdentityNumberUnfilteredList_success() {
        // Using IdentityNumber
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        IdentityNumber identityNumber = personToDelete.getIdentityNumber();
        DeleteCommand deleteCommand = new DeleteCommand(identityNumber);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Test for successfully deleting a person by index
     */
    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Using IdentityNumber
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Test for deleting a person not found in the address book.
     */
    @Test
    public void execute_personNotFound_throwsCommandException() {
        IdentityNumber identityNumber = new IdentityNumber("S8860602G");
        DeleteCommand deleteCommand = new DeleteCommand(identityNumber);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }

    /**
     * Tests validation of input of Delete Command when deleting by index number.
     */
    @Test
    public void validate_outOfBoundIndex_throwsCommandException() {
        Index outOfIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommandByIndex = new DeleteCommand(outOfIndex);

        assertCommandFailure(deleteCommandByIndex, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Tests validation of input of Delete Command when deleting by Index Number with NRIC but no person with the
     * NRIC is found.
     */
    @Test
    public void validate_identityNumber_throwsCommandException() {
        DeleteCommand deleteCommandByIdentityNumber = new DeleteCommand(IDENTITY_NUMBER_NOT_FOUND);
        assertCommandFailure(deleteCommandByIdentityNumber, model, Messages.MESSAGE_PERSON_NOT_FOUND);
    }


    @Test
    public void equals() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        IdentityNumber firstIdentityNumber = firstPerson.getIdentityNumber();
        IdentityNumber secondIdentityNumber = secondPerson.getIdentityNumber();
        DeleteCommand deleteFirstCommand = new DeleteCommand(firstIdentityNumber);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondIdentityNumber);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstIdentityNumber);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        IdentityNumber identityNumber = person.getIdentityNumber();
        DeleteCommand deleteCommand = new DeleteCommand(identityNumber);
        String expected = "Delete person with NRIC: " + identityNumber;
        assertEquals(expected, deleteCommand.toString());
    }


    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
