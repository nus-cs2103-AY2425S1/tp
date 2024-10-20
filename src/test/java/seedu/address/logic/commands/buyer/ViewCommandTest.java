package seedu.address.logic.commands.buyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.TypicalBuyers.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.buyer.BuyerFulfilsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewCommand.
 */
public class ViewCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalMeetUpList());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getMeetUpList());
    }

    @Test
    public void execute_zeroKeywords_showsEverything() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        BuyerFulfilsPredicate predicate = preparePredicate("");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredBuyerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidKeyword_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        BuyerFulfilsPredicate predicate = preparePredicate("hello");
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredBuyerList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredBuyerList());
    }

    @Test
    public void equals() {
        BuyerFulfilsPredicate firstPredicate = new BuyerFulfilsPredicate("");
        BuyerFulfilsPredicate secondPredicate = new BuyerFulfilsPredicate("buyer");

        ViewCommand viewFirstCommand = new ViewCommand(firstPredicate);
        ViewCommand viewSecondCommand = new ViewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(firstPredicate);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different keyword -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        BuyerFulfilsPredicate predicate = new BuyerFulfilsPredicate("buyer");
        ViewCommand viewCommand = new ViewCommand(predicate);
        String expected = ViewCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code BuyerFulfilsPredicate}.
     */
    private BuyerFulfilsPredicate preparePredicate(String userInput) {
        String trimmedArgs = userInput.trim();
        String keyword = trimmedArgs.isEmpty() ? "" : trimmedArgs.split("\\s+")[0];
        return new BuyerFulfilsPredicate(keyword);
    }
}
