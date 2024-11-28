package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARRINE;
import static seedu.address.testutil.TypicalPersons.DANIELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.BuyPropertyContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindBuyCommand}.
 */
public class FindBuyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        BuyPropertyContainsKeywordsPredicate firstPredicate =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("first"));
        BuyPropertyContainsKeywordsPredicate secondPredicate =
                new BuyPropertyContainsKeywordsPredicate(Collections.singletonList("second"));

        FindBuyCommand findFirstCommand = new FindBuyCommand(firstPredicate);
        FindBuyCommand findSecondCommand = new FindBuyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindBuyCommand findFirstCommandCopy = new FindBuyCommand(firstPredicate);
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
        BuyPropertyContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindBuyCommand command = new FindBuyCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        BuyPropertyContainsKeywordsPredicate predicate = preparePredicate("522522 12-33 luxury");
        FindBuyCommand command = new FindBuyCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARRINE, DANIELLE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        BuyPropertyContainsKeywordsPredicate predicate =
                new BuyPropertyContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindBuyCommand findBuyCommand = new FindBuyCommand(predicate);
        String expected = FindBuyCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findBuyCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private BuyPropertyContainsKeywordsPredicate preparePredicate(String userInput) {
        return new BuyPropertyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
