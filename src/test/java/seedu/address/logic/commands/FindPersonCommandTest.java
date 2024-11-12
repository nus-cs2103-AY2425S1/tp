package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBookPersons;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.commons.NameContainsKeywordsPredicate;
import seedu.address.model.commons.RoleContainsKeywordPredicate;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindPersonCommand}.
 */
public class FindPersonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookPersons(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBookPersons(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate<Person> firstPredicate = new NameContainsKeywordsPredicate<>(
                Collections.singletonList("first"));
        NameContainsKeywordsPredicate<Person> secondPredicate = new NameContainsKeywordsPredicate<>(
                Collections.singletonList("second"));

        FindPersonCommand findFirstCommand = new FindPersonCommand(firstPredicate);
        FindPersonCommand findSecondCommand = new FindPersonCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPersonCommand findFirstCommandCopy = new FindPersonCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroNameKeywords_noPersonFound() {
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0),
                true, false, false,
                false, false, false);
        NameContainsKeywordsPredicate<Person> predicate = prepareNamePredicate(" ");
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() {
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3),
                true, false, false,
                false, false, false);
        NameContainsKeywordsPredicate<Person> predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_roleKeyword_noPersonFound() {
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0),
                true, false, false,
                false, false, false);
        RoleContainsKeywordPredicate predicate = prepareRolePredicate("promoter");
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_roleKeyword_multiplePersonsFound() {
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6),
                true, false, false,
                false, false, false);
        RoleContainsKeywordPredicate predicate = prepareRolePredicate("artist");
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndRoleKeyword_noPersonFound() {
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0),
                true, false, false,
                false, false, false);
        NameContainsKeywordsPredicate<Person> namePredicate = prepareNamePredicate("Alice Carl Daniel");
        RoleContainsKeywordPredicate rolePredicate = prepareRolePredicate("organiser");
        Predicate<Person> predicate = namePredicate.and(rolePredicate);
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_nameAndRoleKeyword_multiplePersonsFound() {
        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2),
                true, false, false,
                false, false, false);
        NameContainsKeywordsPredicate<Person> namePredicate = prepareNamePredicate("Alice Bob Carl");
        RoleContainsKeywordPredicate rolePredicate = prepareRolePredicate("artist");
        Predicate<Person> predicate = namePredicate.and(rolePredicate);
        FindPersonCommand command = new FindPersonCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedResult, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate<Person> predicate = new NameContainsKeywordsPredicate<>(Arrays.asList("keyword"));
        FindPersonCommand findPersonCommand = new FindPersonCommand(predicate);
        String expected = FindPersonCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findPersonCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate<Person> prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate<>(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code RoleContainsKeywordPredicate}.
     */
    private RoleContainsKeywordPredicate prepareRolePredicate(String userInput) {
        return new RoleContainsKeywordPredicate(userInput);
    }
}
