package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.logic.Messages;
import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;

public class DeleteEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    /*
    // TODO Update getTypicalAddressBook
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }
    */
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /*
    // TODO Update getTypicalAddressBook
    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_EVENT);

        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showContactAtIndex(model, INDEX_FIRST_EVENT);

        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }
    */
    @Test
    public void equals() {
        DeleteEventCommand deleteFirstEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);
        DeleteEventCommand deleteSecondEventCommand = new DeleteEventCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommand));

        // same values -> returns true
        DeleteEventCommand deleteFirstEventCommandCopy = new DeleteEventCommand(INDEX_FIRST_EVENT);
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstEventCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstEventCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstEventCommand.equals(deleteSecondEventCommand));
    }

    @Test
    public void toStringMethod() {
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST_EVENT);
        String expected = DeleteEventCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_EVENT + "}";
        assertEquals(expected, deleteEventCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no event.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(p -> false);
        assertTrue(model.getFilteredEventList().isEmpty());
    }
}
