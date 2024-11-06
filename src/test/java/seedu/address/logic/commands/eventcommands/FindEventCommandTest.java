package seedu.address.logic.commands.eventcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code FindEventCommand}.
 */
public class FindEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_eventFound_success() {
        // Search for an event with a name that exists in the typical events
        String searchString = "Meeting"; // Replace with actual event name present in getTypicalAddressBook()
        FindEventCommand command = new FindEventCommand(searchString);

        // Update expected model to show events containing the search string
        expectedModel.updateFilteredEventList(event -> event.getName().toString().toLowerCase()
                .contains(searchString.toLowerCase()));

        String expectedMessage = String.format(FindEventCommand.MESSAGE_EVENT_FOUND,
                expectedModel.getFilteredEventList().size(), searchString);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_eventNotFound_noEventsFoundMessage() {
        // Search for a string that does not match any events
        String searchString = "NonExistentEvent";
        FindEventCommand command = new FindEventCommand(searchString);

        String expectedMessage = String.format(FindEventCommand.MESSAGE_EVENT_NOT_FOUND, searchString);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptySearchString_throwsCommandException() {
        String searchString = ""; // An empty search string
        FindEventCommand command = new FindEventCommand(searchString);

        assertCommandFailure(command, model, String.format(FindEventCommand.MESSAGE_EVENT_NOT_FOUND, searchString));
    }

    @Test
    public void equals() {
        FindEventCommand findFirstEventCommand = new FindEventCommand("Meeting");
        FindEventCommand findSecondEventCommand = new FindEventCommand("Conference");

        // same object -> returns true
        assertTrue(findFirstEventCommand.equals(findFirstEventCommand));

        // same values -> returns true
        FindEventCommand findFirstEventCommandCopy = new FindEventCommand("Meeting");
        assertTrue(findFirstEventCommand.equals(findFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(findFirstEventCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstEventCommand.equals(null));

        // different searchString -> returns false
        assertFalse(findFirstEventCommand.equals(findSecondEventCommand));
    }

    @Test
    public void toStringMethod() {
        String searchString = "Meeting";
        FindEventCommand findEventCommand = new FindEventCommand(searchString);

        String expected = "FindEventCommand[searchString=" + searchString + "]";
        assertEquals(expected, findEventCommand.toString());
    }
}
