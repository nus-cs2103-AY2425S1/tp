package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.HAIRCUT;
import static seedu.address.testutil.TypicalEvents.PHOTOSHOOT;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventCelebrityMatchesKeywordPredicate;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterEventCommand}.
 */
public class FilterEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        EventCelebrityMatchesKeywordPredicate firstPredicate =
                new EventCelebrityMatchesKeywordPredicate("james");
        EventCelebrityMatchesKeywordPredicate secondPredicate =
                new EventCelebrityMatchesKeywordPredicate("bronny");

        FilterEventCommand findFirstCommand = new FilterEventCommand(firstPredicate);
        FilterEventCommand findSecondCommand = new FilterEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterEventCommand findFirstCommandCopy = new FilterEventCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                CommandTabChange.EVENT, CommandDetailChange.SIMPLIFIED);
        EventCelebrityMatchesKeywordPredicate predicate = preparePredicate(" ");
        FilterEventCommand command = new FilterEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false,
                CommandTabChange.EVENT, CommandDetailChange.SIMPLIFIED);
        EventCelebrityMatchesKeywordPredicate predicate = preparePredicate("Alice Pauline");
        FilterEventCommand command = new FilterEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(HAIRCUT, PHOTOSHOOT), model.getFilteredEventList());
    }

    @Test
    public void toStringMethod() {
        EventCelebrityMatchesKeywordPredicate predicate = new EventCelebrityMatchesKeywordPredicate("keyword");
        FilterEventCommand findCommand = new FilterEventCommand(predicate);
        String expected = FilterEventCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code EventCelebrityMatchesKeywordPredicate}.
     */
    private EventCelebrityMatchesKeywordPredicate preparePredicate(String userInput) {
        return new EventCelebrityMatchesKeywordPredicate(userInput.trim());
    }
}
