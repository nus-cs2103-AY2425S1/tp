package seedu.address.logic.commands.personcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getExtendedAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.types.common.PersonTagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchPersonCommand}.
 */
public class SearchPersonCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model extendedModel = new ModelManager(getExtendedAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model extendedExpectedModel = new ModelManager(getExtendedAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonTagContainsKeywordsPredicate firstPredicate =
                new PersonTagContainsKeywordsPredicate(Collections.singletonList("first"));
        PersonTagContainsKeywordsPredicate secondPredicate =
                new PersonTagContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchPersonCommand searchFirstCommand = new SearchPersonCommand(firstPredicate);
        SearchPersonCommand searchSecondCommand = new SearchPersonCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchPersonCommand searchFirstCommandCopy = new SearchPersonCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonTagContainsKeywordsPredicate predicate = preparePredicate(" ");
        SearchPersonCommand command = new SearchPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        PersonTagContainsKeywordsPredicate predicate = preparePredicate("Friends Husband");
        SearchPersonCommand command = new SearchPersonCommand(predicate);
        extendedExpectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, extendedModel, expectedMessage, extendedExpectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, BOB), extendedModel.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonTagContainsKeywordsPredicate predicate = new PersonTagContainsKeywordsPredicate(Arrays.asList("keyword"));
        SearchPersonCommand searchPersonCommand = new SearchPersonCommand(predicate);
        String expected = SearchPersonCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, searchPersonCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code PersonTagContainsKeywordsPredicate}.
     */
    private PersonTagContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PersonTagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
