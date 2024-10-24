package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EmailContainsKeywordsPredicate;

public class FindByEmailCommandTest {

    private final Model expectedModel = new ModelManager(getTypicalCampusConnect(), new UserPrefs());
    private final Model model = new ModelManager(getTypicalCampusConnect(), new UserPrefs());

    @Test
    public void equals() {
        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("first"));
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("second"));

        FindByEmailCommand findFirstCommand = new FindByEmailCommand(firstPredicate);
        FindByEmailCommand findSecondCommand = new FindByEmailCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindByEmailCommand findFirstCommandCopy = new FindByEmailCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = SuperFindCommand.MESSAGE_NO_PERSONS_FOUND;
        EmailContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindByEmailCommand command = new FindByEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_resultsWithPartialMatch_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        EmailContainsKeywordsPredicate predicate = preparePredicate("ne");
        FindByEmailCommand command = new FindByEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, ELLE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindByEmailCommand findCommand = new FindByEmailCommand(predicate);
        String expected = FindByEmailCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private EmailContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
