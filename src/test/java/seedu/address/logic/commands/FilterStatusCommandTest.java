package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ADIADI;
import static seedu.address.testutil.TypicalPersons.DOMI;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.StatusContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterStatusCommand}.
 */
public class FilterStatusCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        StatusContainsKeywordsPredicate firstPredicate =
                new StatusContainsKeywordsPredicate(Collections.singletonList("first"));
        StatusContainsKeywordsPredicate secondPredicate =
                new StatusContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterStatusCommand filterStatusFirstCommand = new FilterStatusCommand(firstPredicate);
        FilterStatusCommand filterStatusSecondCommand = new FilterStatusCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterStatusFirstCommand.equals(filterStatusFirstCommand));

        // same values -> returns true
        FilterStatusCommand filterStatusFirstCommandCopy = new FilterStatusCommand(firstPredicate);
        assertTrue(filterStatusFirstCommand.equals(filterStatusFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterStatusFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterStatusFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterStatusFirstCommand.equals(filterStatusSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        StatusContainsKeywordsPredicate predicate = preparePredicate(" ");
        FilterStatusCommand command = new FilterStatusCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        StatusContainsKeywordsPredicate predicate = preparePredicate("Rejected");
        FilterStatusCommand command = new FilterStatusCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ADIADI, DOMI), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        StatusContainsKeywordsPredicate predicate = new StatusContainsKeywordsPredicate(Arrays.asList("keyword"));
        FilterStatusCommand filterStatusCommand = new FilterStatusCommand(predicate);
        String expected = FilterStatusCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterStatusCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private StatusContainsKeywordsPredicate preparePredicate(String userInput) {
        return new StatusContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
