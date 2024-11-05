package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FindCommand.MESSAGE_NO_CONTACTS_FOUND;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.CARL;
import static seedu.address.testutil.TypicalContacts.DANIEL;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContainsKeywordsPredicate;
import seedu.address.testutil.ContainsKeywordsPredicateBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ContainsKeywordsPredicate firstPredicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords("first").withTelegramHandleKeywords("first")
                        .withEmailKeywords("first").withStudentStatusKeywords("first").withRoleKeywords("first")
                        .withNicknameKeywords("first").build();
        ContainsKeywordsPredicate secondPredicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords("first", "second")
                        .withTelegramHandleKeywords("first", "second").withEmailKeywords("first", "second")
                        .withStudentStatusKeywords("first", "second").withRoleKeywords("first", "second")
                        .withNicknameKeywords("first", "second").build();

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

        // different contact -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_validKeywords_noContactFound() {
        String expectedMessage = MESSAGE_NO_CONTACTS_FOUND;
        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords("dfqidjasbkcascief").build();
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertTrue(model.getFilteredContactList().isEmpty());
    }

    @Test
    public void execute_multipleMatchingKeywords_oneContactFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 1);
        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords("Kurz", "Carl").build();
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredContactList());
    }

    @Test
    public void execute_oneMatchingKeyword_multipleContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 2);
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Meier").build();
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredContactList());
    }

    @Test
    public void execute_multipleMatchingKeywords_multipleContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 2);
        ContainsKeywordsPredicate predicate = new ContainsKeywordsPredicateBuilder().withNameKeywords("Meier")
                .withStudentStatusKeywords("undergrad").build();
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredContactList());
    }

    @Test
    public void toStringMethod() {
        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords("keyword").build();
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}
