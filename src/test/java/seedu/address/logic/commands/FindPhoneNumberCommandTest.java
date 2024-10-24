package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PhoneNumberContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPhoneNumberCommand}.
 */
public class FindPhoneNumberCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PhoneNumberContainsKeywordPredicate firstPredicate =
                new PhoneNumberContainsKeywordPredicate(Collections.singletonList("first"));
        PhoneNumberContainsKeywordPredicate secondPredicate =
                new PhoneNumberContainsKeywordPredicate(Collections.singletonList("second"));

        FindPhoneNumberCommand findFirstCommand = new FindPhoneNumberCommand(firstPredicate);
        FindPhoneNumberCommand findSecondCommand = new FindPhoneNumberCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPhoneNumberCommand findFirstCommandCopy = new FindPhoneNumberCommand(firstPredicate);
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
        PhoneNumberContainsKeywordPredicate predicate = preparePredicate(" ");
        FindPhoneNumberCommand command = new FindPhoneNumberCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PhoneNumberContainsKeywordPredicate predicate = preparePredicate("94822 94824 9482442");
        FindPhoneNumberCommand command = new FindPhoneNumberCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PhoneNumberContainsKeywordPredicate predicate =
                new PhoneNumberContainsKeywordPredicate(Arrays.asList("keyword"));
        FindPhoneNumberCommand findPhoneNumberCommand = new FindPhoneNumberCommand(predicate);
        String expected = FindPhoneNumberCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findPhoneNumberCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private PhoneNumberContainsKeywordPredicate preparePredicate(String userInput) {
        return new PhoneNumberContainsKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
