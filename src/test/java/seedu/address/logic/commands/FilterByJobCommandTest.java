package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FilterByJobCommandTest {

    private Model model;
    private Model modelMultiple;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        modelMultiple = new ModelManager();
        Person photographer = new PersonBuilder().withName("Alex").withJob("Photographer").build();
        Person photographer2 = new PersonBuilder().withName("Bob").withJob("Photographer").build();
        Person caterer = new PersonBuilder().withName("Charlie").withJob("Caterer").build();

        model.addPerson(photographer);
        model.addPerson(caterer);
        modelMultiple.addPerson(photographer);
        modelMultiple.addPerson(photographer2);
    }

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
    public void execute_noMatchingKeywords_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        JobContainsKeywordsPredicate predicate = preparePredicate("NonExistentJob");
        FilterByJobCommand command = new FilterByJobCommand(predicate);
        model.updateFilteredPersonList(predicate);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(List.of(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleMatchingKeyword_onePersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        JobContainsKeywordsPredicate predicate = preparePredicate("Photographer");
        FilterByJobCommand command = new FilterByJobCommand(predicate);
        model.updateFilteredPersonList(predicate);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(1, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_singleMatchingKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        JobContainsKeywordsPredicate predicate = preparePredicate("Photographer");
        FilterByJobCommand command = new FilterByJobCommand(predicate);
        modelMultiple.updateFilteredPersonList(predicate);
        assertEquals(expectedMessage, command.execute(modelMultiple).getFeedbackToUser());
        assertEquals(2, modelMultiple.getFilteredPersonList().size());
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
