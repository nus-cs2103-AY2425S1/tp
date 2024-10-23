package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalClientHub;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PhoneBeginsWithKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindPhoneCommandTest {
    private Model model = new ModelManager(getTypicalClientHub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClientHub(), new UserPrefs());

    @Test
    public void equals() {
        PhoneBeginsWithKeywordPredicate firstPredicate =
                new PhoneBeginsWithKeywordPredicate("9876");
        PhoneBeginsWithKeywordPredicate secondPredicate =
                new PhoneBeginsWithKeywordPredicate("9678");

        FindPhoneCommand findPhoneFirstCommand = new FindPhoneCommand(firstPredicate);
        FindPhoneCommand findPhoneSecondCommand = new FindPhoneCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findPhoneFirstCommand.equals(findPhoneFirstCommand));

        // same values -> returns true
        FindPhoneCommand findPhoneFirstCommandCopy = new FindPhoneCommand(firstPredicate);
        assertTrue(findPhoneFirstCommand.equals(findPhoneFirstCommandCopy));

        // different types -> returns false
        assertFalse(findPhoneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findPhoneFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findPhoneFirstCommand.equals(findPhoneSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPhoneFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PhoneBeginsWithKeywordPredicate predicate = preparePredicate(" ");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getDisplayPersons());
    }

    @Test
    public void execute_singleKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PhoneBeginsWithKeywordPredicate predicate = preparePredicate("948");
        FindPhoneCommand command = new FindPhoneCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE), model.getDisplayPersons());
    }

    @Test
    public void toStringMethod() {
        PhoneBeginsWithKeywordPredicate predicate = new PhoneBeginsWithKeywordPredicate("keyword");
        FindPhoneCommand findPhoneCommand = new FindPhoneCommand(predicate);
        String expected = FindPhoneCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findPhoneCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private PhoneBeginsWithKeywordPredicate preparePredicate(String userInput) {
        return new PhoneBeginsWithKeywordPredicate(userInput);
    }
}
