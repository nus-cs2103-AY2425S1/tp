package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.NameOrJobContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class FilterCommandTest {

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
        NameOrJobContainsKeywordsPredicate firstPredicate =
                new NameOrJobContainsKeywordsPredicate(List.of("first"), List.of());
        NameOrJobContainsKeywordsPredicate secondPredicate =
                new NameOrJobContainsKeywordsPredicate(List.of("second"), List.of());

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertEquals(filterFirstCommand, filterFirstCommand);

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
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
        NameOrJobContainsKeywordsPredicate predicate = preparePredicate(" ");
        FilterCommand command = new FilterCommand(predicate);
        model.updateFilteredPersonList(predicate);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(List.of(), model.getFilteredPersonList());
    }

    @Test
    public void execute_noMatchingKeywords_noPersonFound() {
        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameOrJobContainsKeywordsPredicate predicate = preparePredicate("j/NonExistentJob");
        FilterCommand command = new FilterCommand(predicate);
        model.updateFilteredPersonList(predicate);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(List.of(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleMatchingKeyword_onePersonFound() {
        // Ensure the model is correctly set up with the expected persons
        Person photographer = new PersonBuilder().withName("Alex").withJob("Photographer").build();
        model = new ModelManager();
        model.addPerson(photographer);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameOrJobContainsKeywordsPredicate predicate = preparePredicate("j/Photographer");
        FilterCommand command = new FilterCommand(predicate);
        model.updateFilteredPersonList(predicate);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(1, model.getFilteredPersonList().size());
    }

    @Test
    public void execute_singleMatchingKeywords_multiplePersonsFound() {
        // Ensure the model is correctly set up with the expected persons
        Person photographer = new PersonBuilder().withName("Alex").withJob("Photographer").build();
        Person photographer2 = new PersonBuilder().withName("Bob").withJob("Photographer").build();
        modelMultiple = new ModelManager();
        modelMultiple.addPerson(photographer);
        modelMultiple.addPerson(photographer2);

        String expectedMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        NameOrJobContainsKeywordsPredicate predicate = preparePredicate("j/Photographer");
        FilterCommand command = new FilterCommand(predicate);
        modelMultiple.updateFilteredPersonList(predicate);
        assertEquals(expectedMessage, command.execute(modelMultiple).getFeedbackToUser());
        assertEquals(2, modelMultiple.getFilteredPersonList().size());
    }

    @Test
    public void toStringMethod() {
        NameOrJobContainsKeywordsPredicate predicate = new NameOrJobContainsKeywordsPredicate(List.of("keyword"),
                List.of());
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameOrJobContainsKeywordsPredicate}.
     */
    private NameOrJobContainsKeywordsPredicate preparePredicate(String userInput) {
        List<String> nameKeywords = Arrays.stream(userInput.split("\\s+"))
                .filter(keyword -> keyword.startsWith("n/"))
                .map(keyword -> keyword.substring(2))
                .collect(Collectors.toList());

        List<String> jobKeywords = Arrays.stream(userInput.split("\\s+"))
                .filter(keyword -> keyword.startsWith("j/"))
                .map(keyword -> keyword.substring(2))
                .collect(Collectors.toList());

        return new NameOrJobContainsKeywordsPredicate(nameKeywords, jobKeywords);
    }
}
