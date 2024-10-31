package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteEventByIndexCommand}.
 */
public class DeleteEventByIndexCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventByIndexCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST);

        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventByIndexCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        showNoEvent(expectedModel);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        DeleteEventCommand deleteEventCommand = new DeleteEventByIndexCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteEventCommand deleteEventFirstCommand = new DeleteEventByIndexCommand(INDEX_FIRST);
        DeleteEventCommand deleteEventSecondCommand = new DeleteEventByIndexCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteEventFirstCommand.equals(deleteEventFirstCommand));

        // same values -> returns true
        DeleteEventCommand deleteEventFirstCommandCopy = new DeleteEventByIndexCommand(INDEX_FIRST);
        assertTrue(deleteEventFirstCommand.equals(deleteEventFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteEventFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteEventFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteEventFirstCommand.equals(deleteEventSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteEventByIndexCommand deleteEventCommand = new DeleteEventByIndexCommand(targetIndex);
        String expected = DeleteEventByIndexCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteEventCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered event list to show no events.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(e -> false);

        assertTrue(model.getFilteredEventList().isEmpty());
    }
}
