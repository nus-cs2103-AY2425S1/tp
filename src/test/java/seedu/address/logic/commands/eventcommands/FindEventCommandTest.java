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
import seedu.address.model.event.EventNameContainsKeywordPredicate;

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
        EventNameContainsKeywordPredicate predicate = new EventNameContainsKeywordPredicate(searchString);
        FindEventCommand command = new FindEventCommand(predicate);

        // Update expected model to show events containing the search string
        expectedModel.updateFilteredEventList(predicate);

        String expectedMessage = String.format(FindEventCommand.MESSAGE_EVENT_FOUND,
                expectedModel.getFilteredEventList().size(), searchString);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_eventNotFound_noEventsFoundMessage() {
        // Search for a string that does not match any events
        String searchString = "NonExistentEvent";
        EventNameContainsKeywordPredicate predicate = new EventNameContainsKeywordPredicate(searchString);
        FindEventCommand command = new FindEventCommand(predicate);

        String expectedMessage = String.format(FindEventCommand.MESSAGE_EVENT_NOT_FOUND, searchString);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptySearchString_throwsCommandException() {
        String searchString = ""; // An empty search string
        EventNameContainsKeywordPredicate predicate = new EventNameContainsKeywordPredicate(searchString);
        FindEventCommand command = new FindEventCommand(predicate);

        assertCommandFailure(command, model, String.format(FindEventCommand.MESSAGE_EVENT_NOT_FOUND, searchString));
    }

    @Test
    public void equals() {
        EventNameContainsKeywordPredicate predicateFirst = new EventNameContainsKeywordPredicate("Meeting");
        EventNameContainsKeywordPredicate predicateSecond = new EventNameContainsKeywordPredicate("Conference");
        FindEventCommand findFirstEventCommand = new FindEventCommand(predicateFirst);
        FindEventCommand findSecondEventCommand = new FindEventCommand(predicateSecond);

        // same object -> returns true
        assertTrue(findFirstEventCommand.equals(findFirstEventCommand));

        // same values -> returns true
        FindEventCommand findFirstEventCommandCopy = new FindEventCommand(predicateFirst);
        assertTrue(findFirstEventCommand.equals(findFirstEventCommandCopy));

        // different types -> returns false
        assertFalse(findFirstEventCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstEventCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstEventCommand.equals(findSecondEventCommand));
    }

    @Test
    public void toStringMethod() {
        String searchString = "Meeting";
        EventNameContainsKeywordPredicate predicate = new EventNameContainsKeywordPredicate(searchString);
        FindEventCommand findEventCommand = new FindEventCommand(predicate);

        String expected = "FindEventCommand[predicate=" + predicate.toString() + "]";
        assertEquals(expected, findEventCommand.toString());
    }
}
