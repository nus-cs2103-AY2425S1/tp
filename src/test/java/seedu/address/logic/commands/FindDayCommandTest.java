package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.CARL;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DayContainsKeywordsPredicate;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalStudents;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindDayCommandTest {
    private Model model = new ModelManager(TypicalStudents.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        DayContainsKeywordsPredicate firstPredicate =
                new DayContainsKeywordsPredicate(Collections.singletonList("monday"));
        DayContainsKeywordsPredicate secondPredicate =
                new DayContainsKeywordsPredicate(Collections.singletonList("tuesday"));

        FindDayCommand findFirstCommand = new FindDayCommand(firstPredicate);
        FindDayCommand findSecondCommand = new FindDayCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDayCommand findFirstCommandCopy = new FindDayCommand(firstPredicate);
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
        DayContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindDayCommand command = new FindDayCommand(predicate);

        Model expectedModel = new ModelManager(TypicalStudents.getTypicalAddressBook(), new UserPrefs());

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        DayContainsKeywordsPredicate predicate = preparePredicate("monday tuesday");
        FindDayCommand command = new FindDayCommand(predicate);

        Model expectedModel = new ModelManager(TypicalStudents.getTypicalAddressBook(), new UserPrefs());

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_zeroPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DayContainsKeywordsPredicate predicate = preparePredicate("monday tuesday");
        FindDayCommand command = new FindDayCommand(predicate);

        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        DayContainsKeywordsPredicate predicate = new DayContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindDayCommand findCommand = new FindDayCommand(predicate);
        String expected = FindDayCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private DayContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DayContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
