package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_RESTAURANTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestaurants.FIONA;
import static seedu.address.testutil.TypicalRestaurants.GEORGE;
import static seedu.address.testutil.TypicalRestaurants.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.PriceContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindPriceCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PriceContainsKeywordsPredicate firstPredicate =
                new PriceContainsKeywordsPredicate(Collections.singletonList("first"));
        PriceContainsKeywordsPredicate secondPredicate =
                new PriceContainsKeywordsPredicate(Collections.singletonList("second"));

        FindPriceCommand findFirstCommand = new FindPriceCommand(firstPredicate);
        FindPriceCommand findSecondCommand = new FindPriceCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPriceCommand findFirstCommandCopy = new FindPriceCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute() {
        String expectedMessage = String.format(MESSAGE_RESTAURANTS_LISTED_OVERVIEW, 2);
        PriceContainsKeywordsPredicate predicate = preparePredicate("$");
        FindPriceCommand command = new FindPriceCommand(predicate);
        expectedModel.updateFilteredRestaurantList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA, GEORGE), model.getFilteredRestaurantList());
    }

    @Test
    public void toStringMethod() {
        PriceContainsKeywordsPredicate predicate = new PriceContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindPriceCommand findPriceCommand = new FindPriceCommand(predicate);
        String expected = FindPriceCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findPriceCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private PriceContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PriceContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
