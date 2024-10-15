package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MedConContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindMedConCommand}.
 */
public class FindMedConCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("diabetes");
        List<String> secondPredicateKeywordList = Arrays.asList("diabetes", "cancer");

        MedConContainsKeywordsPredicate firstPredicate =
                new MedConContainsKeywordsPredicate(firstPredicateKeywordList);
        MedConContainsKeywordsPredicate secondPredicate =
                new MedConContainsKeywordsPredicate(secondPredicateKeywordList);

        FindMedConCommand findMedConFirstCommand = new FindMedConCommand(firstPredicate);
        FindMedConCommand findMedConSecondCommand = new FindMedConCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findMedConFirstCommand.equals(findMedConFirstCommand));

        // same values -> returns true
        FindMedConCommand findMedConFirstCommandCopy = new FindMedConCommand(firstPredicate);
        assertTrue(findMedConFirstCommand.equals(findMedConFirstCommandCopy));

        // different types -> returns false
        assertFalse(findMedConFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findMedConFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findMedConFirstCommand.equals(findMedConSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noMedConFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        MedConContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindMedConCommand command = new FindMedConCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleKeywords_multipleMedConFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        MedConContainsKeywordsPredicate predicate = preparePredicate("diabetes cancer scoliosis");
        FindMedConCommand command = new FindMedConCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_singleKeyword_multipleMedConFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        MedConContainsKeywordsPredicate predicate = preparePredicate("cancer");
        FindMedConCommand command = new FindMedConCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        MedConContainsKeywordsPredicate predicate = preparePredicate("diabetes");
        FindMedConCommand findCommand = new FindMedConCommand(predicate);
        String expected = FindMedConCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code MedConContainsKeywordsPredicate}.
     */
    private MedConContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MedConContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
