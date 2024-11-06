package seedu.address.logic.commands.eventcommands;

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
 * {@code EventDeleteCommand}.
 */
public class EventDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventDeleteCommand eventDeleteCommand = new EventDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(EventDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(eventDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EventDeleteCommand eventDeleteCommand = new EventDeleteCommand(outOfBoundIndex);

        assertCommandFailure(eventDeleteCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Set up the filtered list to show only the first event
        showEventAtIndex(model, INDEX_FIRST);

        // Get the event to delete
        Event eventToDelete = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventDeleteCommand eventDeleteCommand = new EventDeleteCommand(INDEX_FIRST);

        // Prepare the expected success message
        String expectedMessage = String.format(EventDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        // Create expectedModel and apply the same filter as model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        showNoEvent(expectedModel);

        showNoEvent(expectedModel);

        // Assert command success and that both models are identical
        assertCommandSuccess(eventDeleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // Set up the filtered list to show only the first event
        showEventAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;

        // Ensure the out-of-bound index is within the address book size, but outside the filtered list size
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EventDeleteCommand eventDeleteCommand = new EventDeleteCommand(outOfBoundIndex);

        // Verify that attempting to delete an event at the out-of-bound index throws the correct exception
        assertCommandFailure(eventDeleteCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EventDeleteCommand deleteFirstEventCommand = new EventDeleteCommand(INDEX_FIRST);
        EventDeleteCommand deleteSecondEventCommand = new EventDeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommand));
        // same values -> returns true
        EventDeleteCommand deleteFirstEventCommandCopy = new EventDeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstEventCommand.equals(deleteFirstEventCommandCopy));
        // different types -> returns false
        assertFalse(deleteFirstEventCommand.equals(1));
        // null -> returns false
        assertFalse(deleteFirstEventCommand.equals(null));
        // different event -> returns false
        assertFalse(deleteFirstEventCommand.equals(deleteSecondEventCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        EventDeleteCommand eventDeleteCommand = new EventDeleteCommand(targetIndex);
        String expected = EventDeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, eventDeleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no events.
     */
    private void showNoEvent(Model model) {
        model.updateFilteredEventList(e -> false);
        assertTrue(model.getFilteredEventList().isEmpty());
    }
}
