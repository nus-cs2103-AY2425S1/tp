package seedu.address.logic.commands.findcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.findcommand.FindCommand.MESSAGE_FIND_PERSON_UNSUCCESSFUL;
import static seedu.address.logic.commands.findcommand.FindWeddingCommand.MESSAGE_FIND_WEDDING_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.keywordspredicate.WeddingContainsKeywordsPredicate;

public class FindWeddingCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        WeddingContainsKeywordsPredicate firstPredicate =
                new WeddingContainsKeywordsPredicate(Collections.singletonList("Wedding June 2025"));
        WeddingContainsKeywordsPredicate secondPredicate =
                new WeddingContainsKeywordsPredicate(Collections.singletonList("Lauren's Wedding"));

        FindWeddingCommand findFirstCommand = new FindWeddingCommand(firstPredicate);
        FindWeddingCommand findSecondCommand = new FindWeddingCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindWeddingCommand findFirstCommandCopy = new FindWeddingCommand(firstPredicate);
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
        String expectedMessage = String.format(MESSAGE_FIND_PERSON_UNSUCCESSFUL);
        WeddingContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindWeddingCommand command = new FindWeddingCommand(predicate);
        expectedModel.updateFilteredPersonListByWedding(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        WeddingContainsKeywordsPredicate predicate = preparePredicate("Wedding 2");
        String expectedMessage = String.format(MESSAGE_FIND_WEDDING_PERSON_SUCCESS, predicate.getDisplayString());
        FindWeddingCommand command = new FindWeddingCommand(predicate);
        expectedModel.updateFilteredPersonListByWedding(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialMatchKeyword_multiplePersonsFound() {
        WeddingContainsKeywordsPredicate predicate = preparePredicate("Carl");
        String expectedMessage = String.format(MESSAGE_FIND_WEDDING_PERSON_SUCCESS, predicate.getDisplayString());
        FindWeddingCommand command = new FindWeddingCommand(predicate);
        expectedModel.updateFilteredPersonListByWedding(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_missingKeyword_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_FIND_PERSON_UNSUCCESSFUL);
        WeddingContainsKeywordsPredicate predicate = preparePredicate("XxX");
        FindWeddingCommand command = new FindWeddingCommand(predicate);
        expectedModel.updateFilteredPersonListByWedding(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code WeddingContainsKeywordsPredicate}.
     */
    private WeddingContainsKeywordsPredicate preparePredicate(String userInput) {
        return new WeddingContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
