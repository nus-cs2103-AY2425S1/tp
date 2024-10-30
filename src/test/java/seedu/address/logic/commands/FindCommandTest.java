package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.STUDENT_BENSON;
import static seedu.address.testutil.TypicalPersons.STUDENT_CARL;
import static seedu.address.testutil.TypicalPersons.STUDENT_ELLE;
import static seedu.address.testutil.TypicalPersons.STUDENT_FIONA;
import static seedu.address.testutil.TypicalPersons.TEACHER_ALICE;
import static seedu.address.testutil.TypicalPersons.TEACHER_DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws ParseException {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("/name", "first"));
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("/name", "second"));

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

        // different predicates -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 0);
        PersonContainsKeywordsPredicate predicate = preparePredicate("/name");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_singlePersonFound() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 1);
        PersonContainsKeywordsPredicate predicate = preparePredicate("/name Benson");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(STUDENT_BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 3);
        PersonContainsKeywordsPredicate predicate = preparePredicate("/name Carl Benson Elle");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(STUDENT_BENSON, STUDENT_CARL, STUDENT_ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_caseInsensitive() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 3);
        PersonContainsKeywordsPredicate predicate = preparePredicate("/name CARL elle fiona");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(STUDENT_CARL, STUDENT_ELLE, STUDENT_FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_namePrefix_personFound() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 1);
        PersonContainsKeywordsPredicate predicate = preparePredicate("/name Alice");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(TEACHER_ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_phonePrefix_personFound() throws ParseException {
        String expectedMessage = FindCommand.MESSAGE_SUCCESS;
        PersonContainsKeywordsPredicate predicate = preparePredicate("/contact 94351253");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(TEACHER_ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_addressPrefix_personFound() throws ParseException {
        String expectedMessage = FindCommand.MESSAGE_SUCCESS;
        PersonContainsKeywordsPredicate predicate =
                preparePredicate("/address 123, Jurong West Ave 6, #08-111");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(TEACHER_ALICE, STUDENT_BENSON, STUDENT_ELLE), model.getFilteredPersonList());
    }

    @Test
    public void execute_emailPrefix_personFound() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 1);
        PersonContainsKeywordsPredicate predicate = preparePredicate("/email alice@example.com");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(TEACHER_ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_tagPrefix_personFound() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 1);
        PersonContainsKeywordsPredicate predicate = preparePredicate("t/ friends");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(TEACHER_ALICE, STUDENT_BENSON, TEACHER_DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_noMatchingTag_noPersonFound() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 0);
        PersonContainsKeywordsPredicate predicate = preparePredicate("t/ nonExistentTag");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePrefixes_noPersonFound() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 0);
        PersonContainsKeywordsPredicate predicate = preparePredicate("/name NonExistent /phone 00000000");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePrefixes_personFound() throws ParseException {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS, 1);
        PersonContainsKeywordsPredicate predicate =
                preparePredicate("/name Alice /phone 94351253 /email alice@example.com");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(TEACHER_ALICE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() throws ParseException {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Arrays.asList("/name", "keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code PersonContainsKeywordsPredicate}.
     */
    private PersonContainsKeywordsPredicate preparePredicate(String userInput) throws ParseException {
        return new PersonContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
