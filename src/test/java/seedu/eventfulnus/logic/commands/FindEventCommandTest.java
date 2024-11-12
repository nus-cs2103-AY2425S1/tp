package seedu.eventfulnus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventfulnus.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.eventfulnus.testutil.TypicalEvents.CHESS_COM_NUSC;
import static seedu.eventfulnus.testutil.TypicalEvents.TABLE_TENNIS_LAW_BIZ;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.ModelManager;
import seedu.eventfulnus.model.UserPrefs;
import seedu.eventfulnus.model.event.EventContainsKeywordsPredicate;

public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        EventContainsKeywordsPredicate predicate = preparePredicate("chess table tennis");
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CHESS_COM_NUSC, TABLE_TENNIS_LAW_BIZ), model.getFilteredEventList());
    }

    @Test
    public void execute_partialKeywordMatch_eventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        EventContainsKeywordsPredicate predicate = preparePredicate("che");
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CHESS_COM_NUSC), model.getFilteredEventList());
    }

    @Test
    public void execute_emptyKeyword_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        EventContainsKeywordsPredicate predicate = preparePredicate("ifg");
        FindEventCommand command = new FindEventCommand(predicate);
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        EventContainsKeywordsPredicate predicate = preparePredicate("ifg");
        FindEventCommand command = new FindEventCommand(predicate);
        EventContainsKeywordsPredicate differentPredicate = preparePredicate("ivp");
        FindEventCommand differentCommand = new FindEventCommand(differentPredicate);
        assertFalse(command.equals(differentCommand));
    }

    @Test
    public void toStringMethod() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindEventCommand findEventCommand = new FindEventCommand(predicate);
        String expected = FindEventCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findEventCommand.toString());
    }

    private EventContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EventContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
