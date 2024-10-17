package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DetailContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        DetailContainsKeywordsPredicate firstPredicate =
                new DetailContainsKeywordsPredicate(Collections.singletonList("first"));
        DetailContainsKeywordsPredicate secondPredicate =
                new DetailContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
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
        DetailContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        DetailContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_tagMatch() {
        // Single keyword matches one person's tag
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        DetailContainsKeywordsPredicate predicate = preparePredicate("friends");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Assuming "David Li" has the "mentor" tag in the dataset
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_tagMatch() {
        // Multiple keywords match multiple persons' tags
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        DetailContainsKeywordsPredicate predicate = preparePredicate("runner owesMoney");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Assuming "David Li" and "Irfan Ibrahim" have "mentor" and "tutor" tags respectively
        assertEquals(Arrays.asList(BENSON, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_keywordMatchesBothNameAndTag() {
        // A keyword matches both a person's name and another person's tag
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        DetailContainsKeywordsPredicate predicate = preparePredicate("owesMoney Carl");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Assuming "David Li" has the "mentor" tag and "Carl Kurz" has the name "Carl"
        assertEquals(Arrays.asList(BENSON, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialTagMatch_noPersonFound() {
        // Partial tag matches should not return any persons
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DetailContainsKeywordsPredicate predicate = preparePredicate("fri");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_noTagMatches_noPersonFound() {
        // No matching tag should return an empty list
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DetailContainsKeywordsPredicate predicate = preparePredicate("randomTag");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_caseInsensitiveTagMatch() {
        // Case-insensitive tag matching should work correctly
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        DetailContainsKeywordsPredicate predicate = preparePredicate("OwEsMoneY");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // Assuming "David Li" has the "mentor" tag in the dataset
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        DetailContainsKeywordsPredicate predicate = new DetailContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code DetailContainsKeywordsPredicate}.
     */
    private DetailContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DetailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
