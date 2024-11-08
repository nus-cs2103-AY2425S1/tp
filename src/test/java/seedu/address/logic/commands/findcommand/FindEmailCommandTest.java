package seedu.address.logic.commands.findcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.keywordspredicate.EmailContainsKeywordsPredicate;

public class FindEmailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("bob@yahoo.com"));
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("sally@gmail.com"));

        FindEmailCommand findFirstCommand = new FindEmailCommand(firstPredicate);
        FindEmailCommand findSecondCommand = new FindEmailCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindEmailCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_emptyString_noPersonFound() {
        String expectedMessage = Messages.MESSAGE_FIND_PERSON_UNSUCCESSFUL;
        EmailContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_fullMatchEmail_singlePersonFound() {
        EmailContainsKeywordsPredicate predicate = preparePredicate("alice@example.com");
        String expectedMessage = String.format(
                Messages.MESSAGE_FIND_EMAIL_PERSON_SUCCESS, predicate.getDisplayString()
        );
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleFullMatchEmails_multiplePersonsFound() {
        EmailContainsKeywordsPredicate predicate = preparePredicate("lydia@example.com heinz@example.com");
        String expectedMessage = String.format(
                Messages.MESSAGE_FIND_EMAIL_PERSON_SUCCESS, predicate.getDisplayString()
        );
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_singlePartialMatchNumber_singlePersonFound() {
        EmailContainsKeywordsPredicate predicate = preparePredicate("hnd");
        String expectedMessage = String.format(
                Messages.MESSAGE_FIND_EMAIL_PERSON_SUCCESS, predicate.getDisplayString()
        );
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_singlePartialMatchNumber_multiplePersonsFound() {
        EmailContainsKeywordsPredicate predicate = preparePredicate("ia");
        String expectedMessage = String.format(
                Messages.MESSAGE_FIND_EMAIL_PERSON_SUCCESS, predicate.getDisplayString()
        );
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DANIEL, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_absentPartialMatchKeyword_noPersonFound() {
        EmailContainsKeywordsPredicate predicate = preparePredicate("xXx");
        String expectedMessage = Messages.MESSAGE_FIND_PERSON_UNSUCCESSFUL;
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_absentPartialMatchKeyword2_noPersonFound() {
        EmailContainsKeywordsPredicate predicate = preparePredicate("allen");
        String expectedMessage = Messages.MESSAGE_FIND_PERSON_UNSUCCESSFUL;
        FindEmailCommand command = new FindEmailCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Arrays.asList("amy@gmail.com"));
        FindEmailCommand findPhoneCommand = new FindEmailCommand(predicate);
        String expected = FindEmailCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findPhoneCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    private EmailContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
