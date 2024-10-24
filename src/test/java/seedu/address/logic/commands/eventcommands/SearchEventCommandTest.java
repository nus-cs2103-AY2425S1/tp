package seedu.address.logic.commands.eventcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.ANIME;
import static seedu.address.testutil.TypicalEvents.BARBEQUE;
import static seedu.address.testutil.TypicalEvents.CONCERT;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.types.common.EventTagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchEventCommand}.
 */
public class SearchEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        EventTagContainsKeywordsPredicate firstPredicate =
                new EventTagContainsKeywordsPredicate(Collections.singletonList("first"));
        EventTagContainsKeywordsPredicate secondPredicate =
                new EventTagContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchEventCommand searchFirstCommand = new SearchEventCommand(firstPredicate);
        SearchEventCommand searchSecondCommand = new SearchEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchEventCommand searchFirstCommandCopy = new SearchEventCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventTagContainsKeywordsPredicate predicate = preparePredicate(" ");
        SearchEventCommand command = new SearchEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 3);
        EventTagContainsKeywordsPredicate predicate = preparePredicate("Hobby Music");
        SearchEventCommand command = new SearchEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CONCERT, BARBEQUE, ANIME), model.getFilteredEventList());
    }

    @Test
    public void toStringMethod() {
        EventTagContainsKeywordsPredicate predicate = new EventTagContainsKeywordsPredicate(Arrays.asList("keyword"));
        SearchEventCommand searchEventCommand = new SearchEventCommand(predicate);
        String expected = SearchEventCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, searchEventCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code EventTagContainsKeywordsPredicate}.
     */
    private EventTagContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EventTagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
