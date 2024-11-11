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
 * {@code EventFilterCommand}.
 */
public class EventFilterCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToFilter = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventFilterCommand eventFilterCommand = new EventFilterCommand(INDEX_FIRST);

        String expectedMessage = String.format(EventFilterCommand.MESSAGE_FILTER_EVENT_SUCCESS,
                eventToFilter.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.filterEvent(eventToFilter);

        assertCommandSuccess(eventFilterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EventFilterCommand eventFilterCommand = new EventFilterCommand(outOfBoundIndex);

        assertCommandFailure(eventFilterCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // Set up the filtered list to show only the first event
        showEventAtIndex(model, INDEX_FIRST);

        // Get the event to filter
        Event eventToFilter = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());
        EventFilterCommand eventFilterCommand = new EventFilterCommand(INDEX_FIRST);

        // Prepare the expected success message
        String expectedMessage = String.format(EventFilterCommand.MESSAGE_FILTER_EVENT_SUCCESS,
                eventToFilter.getName());

        // Create expectedModel and apply the same filter as model
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showEventAtIndex(expectedModel, INDEX_FIRST); // Apply the same filter to expectedModel

        expectedModel.filterEvent(eventToFilter);

        assertCommandSuccess(eventFilterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // Set up the filtered list to show only the first event
        showEventAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;

        // Ensure the out-of-bound index is within the address book size but outside the filtered list size
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        EventFilterCommand eventFilterCommand = new EventFilterCommand(outOfBoundIndex);

        assertCommandFailure(eventFilterCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EventFilterCommand filterFirstEventCommand = new EventFilterCommand(INDEX_FIRST);
        EventFilterCommand filterSecondEventCommand = new EventFilterCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(filterFirstEventCommand.equals(filterFirstEventCommand));

        // same values -> returns true
        EventFilterCommand filterFirstEventCommandCopy = new EventFilterCommand(INDEX_FIRST);
        assertTrue(filterFirstEventCommand.equals(filterFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstEventCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstEventCommand.equals(null));

        // different index -> returns false
        assertFalse(filterFirstEventCommand.equals(filterSecondEventCommand));
    }

    @Test
    public void toStringMethod() {
        // Arrange: Create an instance of EventFilterCommand with a specific target index
        Index targetIndex = Index.fromOneBased(1);
        EventFilterCommand eventFilterCommand = new EventFilterCommand(targetIndex);

        // Act: Expected format based on the ToStringBuilder implementation
        String expected = EventFilterCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";

        // Assert: Check if toString() matches the expected format
        assertEquals(expected, eventFilterCommand.toString());
    }
}
