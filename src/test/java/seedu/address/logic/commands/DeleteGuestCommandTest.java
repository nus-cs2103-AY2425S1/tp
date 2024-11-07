package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGuestAtIndex;
import static seedu.address.testutil.TypicalGuests.getTypicalAddressBookWithGuests;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Guest;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteGuestCommand}.
 */
public class DeleteGuestCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithGuests(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Guest personToDelete = (Guest) model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteGuestCommand deleteGuestCommand = new DeleteGuestCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteGuestCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteGuestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);
        DeleteGuestCommand deleteGuestCommand = new DeleteGuestCommand(outOfBoundIndex);

        assertCommandFailure(deleteGuestCommand, model, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);

        Guest personToDelete = (Guest) model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteGuestCommand deleteGuestCommand = new DeleteGuestCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteGuestCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteGuestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteGuestCommand deleteGuestCommand = new DeleteGuestCommand(outOfBoundIndex);

        assertCommandFailure(deleteGuestCommand, model, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteGuestCommand deleteFirstCommand = new DeleteGuestCommand(INDEX_FIRST_PERSON);
        DeleteGuestCommand deleteSecondCommand = new DeleteGuestCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteGuestCommand deleteFirstCommandCopy = new DeleteGuestCommand(INDEX_FIRST_PERSON);
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
        DeleteGuestCommand deleteCommand = new DeleteGuestCommand(targetIndex);
        String expected = DeleteGuestCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredGuestList().isEmpty());
    }
}
