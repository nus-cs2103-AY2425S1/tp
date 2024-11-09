package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CLIENTS_FOUND_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.CARL;
import static seedu.address.testutil.TypicalClients.ELLE;
import static seedu.address.testutil.TypicalClients.FIONA;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.ClientContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ClientContainsKeywordsPredicate firstPredicate =
                new ClientContainsKeywordsPredicate(Collections.singletonList("first"));
        ClientContainsKeywordsPredicate secondPredicate =
                new ClientContainsKeywordsPredicate(Collections.singletonList("second"));

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

        // different client -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleKeywords_noClientFound() {
        ClientContainsKeywordsPredicate predicate = preparePredicate("abc def");
        String expectedMessage = String.format(MESSAGE_CLIENTS_FOUND_OVERVIEW, 0, "[abc, def]");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredClientList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredClientList());
    }

    @Test
    public void execute_multipleKeywords_multipleClientsFound() {
        ClientContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        String expectedMessage = String.format(MESSAGE_CLIENTS_FOUND_OVERVIEW, 3, "[Kurz, Elle, Kunz]");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredClientList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredClientList());
    }

    @Test
    public void execute_multipleKeywords_multipleClientsAndCompaniesFound() {
        ClientContainsKeywordsPredicate predicate = preparePredicate("Elle Kunz apple Happle");
        String expectedMessage = String.format(MESSAGE_CLIENTS_FOUND_OVERVIEW, 3, "[Elle, Kunz, apple, Happle]");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredClientList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE, FIONA), model.getFilteredClientList());
    }

    @Test
    public void execute_transactionListView_throwsCommandException() {
        ClientContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand findCommand = new FindCommand(predicate);
        model.setIsViewTransactions(true);
        String expectedMessage = String.format(Messages.MESSAGE_MUST_BE_CLIENT_LIST, "find");
        assertCommandFailure(findCommand, model, expectedMessage);
    }

    @Test
    public void toStringMethod() {
        ClientContainsKeywordsPredicate predicate = new ClientContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ClientContainsKeywordsPredicate}.
     */
    private ClientContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ClientContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
