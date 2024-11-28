package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BILL;
import static seedu.address.testutil.TypicalPersons.DANIELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.SellPropertyContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindSellCommand}.
 */
public class FindSellCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SellPropertyContainsKeywordsPredicate firstPredicate =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("first"));
        SellPropertyContainsKeywordsPredicate secondPredicate =
                new SellPropertyContainsKeywordsPredicate(Collections.singletonList("second"));

        FindSellCommand findFirstCommand = new FindSellCommand(firstPredicate);
        FindSellCommand findSecondCommand = new FindSellCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindSellCommand findFirstCommandCopy = new FindSellCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SellPropertyContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindSellCommand command = new FindSellCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        SellPropertyContainsKeywordsPredicate predicate = preparePredicate("544488 16-65 penthouse");
        FindSellCommand command = new FindSellCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BILL, DANIELLE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        SellPropertyContainsKeywordsPredicate predicate =
                new SellPropertyContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindSellCommand findSellCommand = new FindSellCommand(predicate);
        String expected = FindSellCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findSellCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private SellPropertyContainsKeywordsPredicate preparePredicate(String userInput) {
        return new SellPropertyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
