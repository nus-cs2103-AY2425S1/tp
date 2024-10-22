package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BILL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.DANIELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TagContactContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindTagContactCommand}.
 */
public class FindTagContactCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagContactContainsKeywordPredicate firstPredicate =
                new TagContactContainsKeywordPredicate(Collections.singletonList("first"));
        TagContactContainsKeywordPredicate secondPredicate =
                new TagContactContainsKeywordPredicate(Collections.singletonList("second"));

        FindTagContactCommand findFirstCommand = new FindTagContactCommand(firstPredicate);
        FindTagContactCommand findSecondCommand = new FindTagContactCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTagContactCommand findFirstCommandCopy = new FindTagContactCommand(firstPredicate);
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
        TagContactContainsKeywordPredicate predicate = preparePredicate(" ");
        FindTagContactCommand command = new FindTagContactCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        TagContactContainsKeywordPredicate predicate = preparePredicate("owes friend");
        FindTagContactCommand command = new FindTagContactCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, BILL, DANIEL, DANIELLE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        TagContactContainsKeywordPredicate predicate =
                new TagContactContainsKeywordPredicate(Arrays.asList("keyword"));
        FindTagContactCommand findTagContactCommand = new FindTagContactCommand(predicate);
        String expected = FindTagContactCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findTagContactCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code TagContactContainsKeyword}.
     */
    private TagContactContainsKeywordPredicate preparePredicate(String userInput) {
        return new TagContactContainsKeywordPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
