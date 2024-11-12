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
 * {@code EventViewCommand}.
 */
public class EventViewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToView = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventViewCommand eventViewCommand = new EventViewCommand(INDEX_FIRST);

        String expectedMessage = String.format(EventViewCommand.MESSAGE_VIEW_EVENT_SUCCESS, eventToView.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.viewEvent(eventToView);

        assertCommandSuccess(eventViewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EventViewCommand eventViewCommand = new EventViewCommand(outOfBoundIndex);

        assertCommandFailure(eventViewCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Set up the filtered list to show only the first event
        showEventAtIndex(model, INDEX_FIRST);

        // Get the event to view
        Event eventToView = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventViewCommand eventViewCommand = new EventViewCommand(INDEX_FIRST);

        // Prepare the expected success message
        String expectedMessage = String.format(EventViewCommand.MESSAGE_VIEW_EVENT_SUCCESS, eventToView.getName());

        // Create expectedModel and apply the same filter as model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showEventAtIndex(expectedModel, INDEX_FIRST); // Apply the same filter to expectedModel

        expectedModel.viewEvent(eventToView);

        assertCommandSuccess(eventViewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // Set up the filtered list to show only the first event
        showEventAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;

        // Ensure the out-of-bound index is within the address book size but outside the filtered list size
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EventViewCommand eventViewCommand = new EventViewCommand(outOfBoundIndex);

        assertCommandFailure(eventViewCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EventViewCommand viewFirstEventCommand = new EventViewCommand(INDEX_FIRST);
        EventViewCommand viewSecondEventCommand = new EventViewCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(viewFirstEventCommand.equals(viewFirstEventCommand));

        // same values -> returns true
        EventViewCommand viewFirstEventCommandCopy = new EventViewCommand(INDEX_FIRST);
        assertTrue(viewFirstEventCommand.equals(viewFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstEventCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstEventCommand.equals(null));

        // different index -> returns false
        assertFalse(viewFirstEventCommand.equals(viewSecondEventCommand));
    }

    @Test
    public void toStringMethod() {
        // Arrange: Create an instance of EventViewCommand with a specific target index
        Index targetIndex = Index.fromOneBased(1);
        EventViewCommand eventViewCommand = new EventViewCommand(targetIndex);

        // Act: Expected format based on the ToStringBuilder implementation
        String expected = EventViewCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";

        // Assert: Check if toString() matches the expected format
        assertEquals(expected, eventViewCommand.toString());
    }
}
