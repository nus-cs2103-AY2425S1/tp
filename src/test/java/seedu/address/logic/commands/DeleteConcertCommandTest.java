package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showConcertAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBookConcerts;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONCERT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.concert.Concert;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteConcertCommand}.
 */
public class DeleteConcertCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookConcerts(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Concert concertToDelete = model.getFilteredConcertList().get(INDEX_FIRST_CONCERT
                .getZeroBased());
        DeleteConcertCommand deleteCommand = new DeleteConcertCommand(INDEX_FIRST_CONCERT);

        String expectedMessage = String.format(DeleteConcertCommand.MESSAGE_DELETE_CONCERT_SUCCESS, Messages
                .format(concertToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteConcert(concertToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredConcertList().size() + 1);
        DeleteConcertCommand deleteCommand = new DeleteConcertCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showConcertAtIndex(model, INDEX_FIRST_CONCERT);

        Concert concertToDelete = model.getFilteredConcertList().get(INDEX_FIRST_CONCERT
                .getZeroBased());
        DeleteConcertCommand deleteCommand = new DeleteConcertCommand(INDEX_FIRST_CONCERT);

        String expectedMessage = String.format(DeleteConcertCommand.MESSAGE_DELETE_CONCERT_SUCCESS, Messages
                .format(concertToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteConcert(concertToDelete);
        showNoConcert(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showConcertAtIndex(model, INDEX_FIRST_CONCERT);

        Index outOfBoundIndex = INDEX_SECOND_CONCERT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getConcertList().size());

        DeleteConcertCommand deleteCommand = new DeleteConcertCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteConcertCommand deleteFirstCommand = new DeleteConcertCommand(INDEX_FIRST_CONCERT);
        DeleteConcertCommand deleteSecondCommand = new DeleteConcertCommand(INDEX_SECOND_CONCERT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteConcertCommand deleteFirstCommandCopy = new DeleteConcertCommand(INDEX_FIRST_CONCERT);
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
        DeleteConcertCommand deleteCommand = new DeleteConcertCommand(targetIndex);
        String expected = DeleteConcertCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     * @param model
     */
    private void showNoConcert(Model model) {
        model.updateFilteredConcertList(p -> false);

        assertTrue(model.getFilteredConcertList().isEmpty());
    }
}
