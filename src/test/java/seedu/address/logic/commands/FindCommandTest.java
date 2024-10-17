package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_NO_STUDENTS_FOUND;
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
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentIdMatchesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Predicate<Person> firstPredicate = new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        Predicate<Person> secondPredicate = new NameContainsKeywordsPredicate(Collections.singletonList("second"));

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

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // Combined predicate (Name and Student ID)
        CompositePredicate combinedPredicateFirst = new CompositePredicate();
        combinedPredicateFirst.addPredicate(new NameContainsKeywordsPredicate(Collections.singletonList("first")));
        combinedPredicateFirst.addPredicate(new StudentIdMatchesPredicate(Collections.singletonList("A1234567E")));

        CompositePredicate combinedPredicateSecond = new CompositePredicate();
        combinedPredicateSecond.addPredicate(new NameContainsKeywordsPredicate(Collections.singletonList("second")));
        combinedPredicateSecond.addPredicate(new StudentIdMatchesPredicate(Collections.singletonList("A7654321E")));

        FindCommand findCombinedFirstCommand = new FindCommand(combinedPredicateFirst);
        FindCommand findCombinedSecondCommand = new FindCommand(combinedPredicateSecond);

        assertFalse(findCombinedFirstCommand.equals(findCombinedSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_NO_STUDENTS_FOUND, 0);
        Predicate<Person> predicate = prepareNamePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleNameKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        Predicate<Person> predicate = prepareNamePredicate("Meier");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        List<Person> expectedList = Arrays.asList(BENSON, DANIEL);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        Predicate<Person> predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleStudentId_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        Predicate<Person> predicate = prepareStudentIdPredicate("A1234567P");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        List<Person> expectedList = Arrays.asList(ALICE);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleStudentIds_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        Predicate<Person> predicate = prepareStudentIdPredicate("A1234567P A0000000P");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        List<Person> expectedList = Arrays.asList(ALICE, BENSON);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndStudentId_personsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        CompositePredicate predicate = new CompositePredicate();
        predicate.addPredicate(new NameContainsKeywordsPredicate(Arrays.asList("Alice")));
        predicate.addPredicate(new StudentIdMatchesPredicate(Arrays.asList("A0000000P")));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        List<Person> expectedList = Arrays.asList(ALICE, BENSON);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    @Test
    public void execute_caseInsensitiveNameMatching_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        Predicate<Person> predicate = prepareNamePredicate("aLiCe");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        List<Person> expectedList = Arrays.asList(ALICE);
        assertEquals(expectedList, model.getFilteredPersonList());
    }

    @Test
    public void execute_fullWordNameMatching_personNotFound() {
        String expectedMessage = MESSAGE_NO_STUDENTS_FOUND;
        Predicate<Person> predicate = prepareNamePredicate("Al");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_partialStudentIdMatching_personNotFound() {
        String expectedMessage = MESSAGE_NO_STUDENTS_FOUND;
        Predicate<Person> predicate = prepareStudentIdPredicate("A016");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_noPersonsInModel_noPersonFound() {
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        String expectedMessage = MESSAGE_NO_STUDENTS_FOUND;
        Predicate<Person> predicate = prepareNamePredicate("Alice");
        FindCommand command = new FindCommand(predicate);
        expectedEmptyModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, emptyModel, expectedMessage, expectedEmptyModel);
        assertEquals(Collections.emptyList(), emptyModel.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private Predicate<Person> prepareNamePredicate(String userInput) {
        List<String> keywords = Arrays.asList(userInput.trim().split("\\s+"));
        return new NameContainsKeywordsPredicate(keywords);
    }

    /**
     * Parses {@code userInput} into a {@code StudentIdMatchesPredicate}.
     */
    private Predicate<Person> prepareStudentIdPredicate(String userInput) {
        List<String> studentIds = Arrays.asList(userInput.trim().split("\\s+"));
        return new StudentIdMatchesPredicate(studentIds);
    }
}
