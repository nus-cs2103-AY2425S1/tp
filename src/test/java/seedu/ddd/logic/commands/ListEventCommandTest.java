package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.TypicalEvents.WEDDING_A;
import static seedu.ddd.testutil.TypicalEvents.WEDDING_B;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;
import seedu.ddd.model.event.common.EventId;
import seedu.ddd.model.event.common.predicate.DescriptionContainsKeywordsPredicate;
import seedu.ddd.model.event.common.predicate.EventIdPredicate;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListEventCommand.
 */
public class ListEventCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        expectedModel.updateFilteredContactList(ListEventCommand.CLEAR_CONTACTS);
        assertCommandSuccess(new ListEventCommand(Model.PREDICATE_SHOW_ALL_EVENTS),
                model, String.format(MESSAGE_EVENTS_LISTED_OVERVIEW,
                        expectedModel.getFilteredEventListSize()), expectedModel);
    }

    @Test
    public void executeFilterListById() {
        expectedModel.updateFilteredContactList(ListEventCommand.CLEAR_CONTACTS);
        expectedModel.updateFilteredEventList(new EventIdPredicate(new EventId(1)));
        assertCommandSuccess(new ListEventCommand(new EventIdPredicate(new EventId(1))),
                model, String.format(MESSAGE_EVENTS_LISTED_OVERVIEW,
                        expectedModel.getFilteredEventListSize()), expectedModel);
    }

    @Test
    public void equals() {
        DescriptionContainsKeywordsPredicate firstPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("first"));
        DescriptionContainsKeywordsPredicate secondPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("second"));
        ListEventCommand listFirstCommand = new ListEventCommand(firstPredicate);
        ListEventCommand listSecondCommand = new ListEventCommand(secondPredicate);

        // same object -> return true
        assertTrue(listFirstCommand.equals(listFirstCommand));

        // same values -> return true
        ListEventCommand listFirstCommandCopy = new ListEventCommand(firstPredicate);
        assertTrue(listFirstCommand.equals(listFirstCommandCopy));

        // different types -> return false
        assertFalse(listFirstCommand.equals(1));

        // null -> return false
        assertFalse(listFirstCommand.equals(null));

        // different person -> return false
        assertFalse(listFirstCommand.equals(listSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate(" ");
        ListEventCommand command = new ListEventCommand(predicate);

        expectedModel.updateFilteredContactList(ListEventCommand.CLEAR_CONTACTS);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }
    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate("wedding");
        ListEventCommand command = new ListEventCommand(predicate);

        expectedModel.updateFilteredContactList(ListEventCommand.CLEAR_CONTACTS);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(WEDDING_A, WEDDING_B), model.getFilteredEventList());
    }
    @Test
    public void toStringMethod() {
        DescriptionContainsKeywordsPredicate predicateOne =
                new DescriptionContainsKeywordsPredicate(Arrays.asList("keyword"));
        EventIdPredicate predicateTwo = new EventIdPredicate(new EventId(1));

        ListEventCommand listEventCommandOne = new ListEventCommand(predicateOne);
        ListEventCommand listEventCommandTwo = new ListEventCommand(predicateTwo);

        String expectedOne = ListEventCommand.class.getCanonicalName() + "{predicate=" + predicateOne + "}";
        String expectedTwo = ListEventCommand.class.getCanonicalName() + "{predicate=" + predicateTwo + "}";

        assertEquals(expectedOne, listEventCommandOne.toString());
        assertEquals(expectedTwo, listEventCommandTwo.toString());

    }
    private DescriptionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
