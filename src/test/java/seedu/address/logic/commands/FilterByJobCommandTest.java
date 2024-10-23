package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.JobContainsKeywordsPredicate;

public class FilterByJobCommandTest {

    private Model model = new ModelManager();

    @Test
    public void equals() {
        JobContainsKeywordsPredicate firstPredicate =
                new JobContainsKeywordsPredicate(List.of("first"));
        JobContainsKeywordsPredicate secondPredicate =
                new JobContainsKeywordsPredicate(List.of("second"));

        FilterByJobCommand filterFirstCommand = new FilterByJobCommand(firstPredicate);
        FilterByJobCommand filterSecondCommand = new FilterByJobCommand(secondPredicate);

        // same object -> returns true
        assertEquals(filterFirstCommand, filterFirstCommand);

        // same values -> returns true
        FilterByJobCommand filterFirstCommandCopy = new FilterByJobCommand(firstPredicate);
        assertEquals(filterFirstCommand, filterFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(filterFirstCommand, 1);

        // null -> returns false
        assertNotEquals(filterFirstCommand, null);

        // different person -> returns false
        assertNotEquals(filterFirstCommand, filterSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        JobContainsKeywordsPredicate predicate = preparePredicate(" ");
        FilterByJobCommand command = new FilterByJobCommand(predicate);
        model.updateFilteredPersonList(predicate);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(List.of(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        JobContainsKeywordsPredicate predicate = new JobContainsKeywordsPredicate(Arrays.asList("keyword"));
        FilterByJobCommand filterByJobCommand = new FilterByJobCommand(predicate);
        String expected = FilterByJobCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterByJobCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code JobContainsKeywordsPredicate}.
     */
    private JobContainsKeywordsPredicate preparePredicate(String userInput) {
        return new JobContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
