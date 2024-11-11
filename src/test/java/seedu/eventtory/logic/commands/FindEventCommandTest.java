package seedu.eventtory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.eventtory.logic.Messages.MESSAGE_NO_EVENTS_FOUND;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventtory.testutil.TypicalEvents.CARL;
import static seedu.eventtory.testutil.TypicalEvents.ELLE;
import static seedu.eventtory.testutil.TypicalEvents.FIONA;
import static seedu.eventtory.testutil.TypicalEvents.getTypicalEventTory;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.eventtory.model.Model;
import seedu.eventtory.model.ModelManager;
import seedu.eventtory.model.UserPrefs;
import seedu.eventtory.model.event.EventContainsKeywordsPredicate;
import seedu.eventtory.ui.UiState;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalEventTory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalEventTory(), new UserPrefs());

    @Test
    public void equals() {
        EventContainsKeywordsPredicate firstPredicate =
                new EventContainsKeywordsPredicate(Collections.singletonList("first"));
        EventContainsKeywordsPredicate secondPredicate =
                new EventContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy = new FindEventCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFoundError() {
        EventContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandFailure(command, model, MESSAGE_NO_EVENTS_FOUND);
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        EventContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredEventList());
    }

    @Test
    public void toStringMethod() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindEventCommand findCommand = new FindEventCommand(predicate);
        String expected = FindEventCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    @Test
    public void execute_wrongView_invalidViewError() {
        model.setUiState(UiState.VENDOR_LIST);
        EventContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindEventCommand command = new FindEventCommand(predicate);
        assertCommandFailure(command, model, FindEventCommand.MESSAGE_FIND_EVENT_FAILURE_INVALID_VIEW);
    }

    /**
     * Parses {@code userInput} into a {@code EventContainsKeywordsPredicate}.
     */
    private EventContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EventContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
