package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContainsGeneralKeywordsPredicate;
import seedu.address.model.person.ContainsSpecificKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        //Test for find command with general predicate
        ContainsGeneralKeywordsPredicate firstGeneralPredicate =
                new ContainsGeneralKeywordsPredicate(Collections.singletonList("first"));
        ContainsGeneralKeywordsPredicate secondGeneralPredicate =
                new ContainsGeneralKeywordsPredicate(Collections.singletonList("second"));

        FindCommand generalFindFirstCommand = new FindCommand(firstGeneralPredicate);
        FindCommand generalFindSecondCommand = new FindCommand(secondGeneralPredicate);

        // same object -> returns true
        assertTrue(generalFindFirstCommand.equals(generalFindFirstCommand));

        // same values -> returns true
        FindCommand generalFindFirstCommandCopy = new FindCommand(firstGeneralPredicate);
        assertTrue(generalFindFirstCommand.equals(generalFindFirstCommandCopy));

        // different types -> returns false
        assertFalse(generalFindFirstCommand.equals(1));

        // null -> returns false
        assertFalse(generalFindFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(generalFindFirstCommand.equals(generalFindSecondCommand));

        //Test for find command with specific predicate
        ContainsSpecificKeywordsPredicate firstSpecificPredicate =
                new ContainsSpecificKeywordsPredicate(Collections.singletonList("first"));
        ContainsSpecificKeywordsPredicate secondSpecificPredicate =
                new ContainsSpecificKeywordsPredicate(Collections.singletonList("second"));

        FindCommand specificFindFirstCommand = new FindCommand(firstSpecificPredicate);
        FindCommand specificFindSecondCommand = new FindCommand(secondSpecificPredicate);

        // same object -> returns true
        assertTrue(specificFindFirstCommand.equals(specificFindFirstCommand));

        // same values -> returns true
        FindCommand specificFindFirstCommandCopy = new FindCommand(firstSpecificPredicate);
        assertTrue(specificFindFirstCommand.equals(specificFindFirstCommandCopy));

        // different types -> returns false
        assertFalse(specificFindFirstCommand.equals(1));

        // null -> returns false
        assertFalse(specificFindFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(specificFindFirstCommand.equals(specificFindSecondCommand));

        //Test for comparing find command with general and specific predicate
        assertFalse(specificFindFirstCommand.equals(generalFindFirstCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String generalExpectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ContainsGeneralKeywordsPredicate generalPredicate = prepareGeneralPredicate(" ");
        FindCommand generalKeywordsFindcommand = new FindCommand(generalPredicate);
        expectedModel.updateFilteredPersonList(generalPredicate);
        assertCommandSuccess(generalKeywordsFindcommand, model, generalExpectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_generalPredicate_multipleKeywords_multiplePersonsFound() {
        String generalPredicateExpectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ContainsGeneralKeywordsPredicate generalPredicate = prepareGeneralPredicate("Kurz Elle Kunz");
        FindCommand generalCommand = new FindCommand(generalPredicate);
        expectedModel.updateFilteredPersonList(generalPredicate);
        assertCommandSuccess(generalCommand, model, generalPredicateExpectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_specificPredicate_singleKeyword_singlePersonFound() {
        String specificPredicateExpectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ContainsSpecificKeywordsPredicate specificPredicate = prepareSpecificPredicate("Carl Kurz");
        FindCommand specificCommand = new FindCommand(specificPredicate);
        expectedModel.updateFilteredPersonList(specificPredicate);
        assertCommandSuccess(specificCommand, model, specificPredicateExpectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_specificPredicate_multipleKeyword_singlePersonFound() {
        String specificPredicateExpectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        String[] input = { "Daniel Meier", "10th street"};
        ContainsSpecificKeywordsPredicate specificPredicate = prepareSpecificPredicate(Arrays.stream(input).toList());
        FindCommand specificCommand = new FindCommand(specificPredicate);
        expectedModel.updateFilteredPersonList(specificPredicate);
        assertCommandSuccess(specificCommand, model, specificPredicateExpectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
    ContainsGeneralKeywordsPredicate generalPredicate =
            new ContainsGeneralKeywordsPredicate(Arrays.asList("keyword"));
    FindCommand findCommandWithGeneralPredicate = new FindCommand(generalPredicate);
    String generalPredicateExpected =
            FindCommand.class.getCanonicalName() + "{specific predicate=null, general predicate="
            + generalPredicate + "}";
    assertEquals(generalPredicateExpected, findCommandWithGeneralPredicate.toString());

    ContainsSpecificKeywordsPredicate specificPredicate =
            new ContainsSpecificKeywordsPredicate(Arrays.asList("keyword"));
    FindCommand findCommandWithSpecificPredicate = new FindCommand(specificPredicate);
    String specificPredicateExpected =
            FindCommand.class.getCanonicalName() + "{specific predicate=" + specificPredicate +
            ", general predicate=null}";
    assertEquals(specificPredicateExpected, findCommandWithSpecificPredicate.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ContainsGeneralKeywordsPredicate}.
     */
    private ContainsGeneralKeywordsPredicate prepareGeneralPredicate(String userInput) {
        return new ContainsGeneralKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code ContainsSpecificKeywordsPredicate}.
     */
    private ContainsSpecificKeywordsPredicate prepareSpecificPredicate(String userInput) {
        return new ContainsSpecificKeywordsPredicate(Arrays.asList(userInput));
    }

    /**
     * Parses {@code userInput} into a {@code ContainsSpecificKeywordsPredicate}.
     */
    private ContainsSpecificKeywordsPredicate prepareSpecificPredicate(List<String> userInput) {
        return new ContainsSpecificKeywordsPredicate(userInput);
    }
}
