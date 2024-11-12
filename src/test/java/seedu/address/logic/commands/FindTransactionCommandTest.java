package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.testutil.TypicalClients.CARL;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.TransactionContainsKeywordsPredicate;

public class FindTransactionCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TransactionContainsKeywordsPredicate firstPredicate =
                new TransactionContainsKeywordsPredicate(Collections.singletonList("first"));
        TransactionContainsKeywordsPredicate secondPredicate =
                new TransactionContainsKeywordsPredicate(Collections.singletonList("second"));

        FindTransactionCommand findFirstCommand = new FindTransactionCommand(firstPredicate);
        FindTransactionCommand findSecondCommand = new FindTransactionCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTransactionCommand findFirstCommandCopy = new FindTransactionCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTransactionFound() {
        // Find transactions of Carl
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 0, Messages.format(CARL));
        TransactionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTransactionCommand command = new FindTransactionCommand(predicate);
        // set client list to contain only the target client
        showClientAtIndex(model, Index.fromOneBased(3));
        showClientAtIndex(expectedModel, Index.fromOneBased(3));
        // set isViewTransactions to true
        model.setIsViewTransactions(true);
        expectedModel.setIsViewTransactions(true);
        // update transaction list to contain only the target client's transactions
        model.updateTransactionList(CARL.getTransactions());
        expectedModel.updateTransactionList(CARL.getTransactions());
        // find transactions in expectedModel
        expectedModel.updateTransactionListPredicate(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_multipleKeywords_multipleTransactionsFound() {
        // Find transactions of Carl
        String expectedMessage = String.format(MESSAGE_TRANSACTIONS_LISTED_OVERVIEW, 2, Messages.format(CARL));
        TransactionContainsKeywordsPredicate predicate = preparePredicate("raw materials invest");
        FindTransactionCommand command = new FindTransactionCommand(predicate);
        // set client list to contain only the target client
        showClientAtIndex(model, Index.fromOneBased(3));
        showClientAtIndex(expectedModel, Index.fromOneBased(3));
        // set isViewTransactions to true
        model.setIsViewTransactions(true);
        expectedModel.setIsViewTransactions(true);
        // update transaction list to contain only the target client's transactions
        model.updateTransactionList(CARL.getTransactions());
        expectedModel.updateTransactionList(CARL.getTransactions());
        // find transactions in expectedModel
        expectedModel.updateTransactionListPredicate(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(CARL.getTransactions(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_clientListView_throwsCommandException() {
        TransactionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindTransactionCommand command = new FindTransactionCommand(predicate);
        model.setIsViewTransactions(false);
        String expectedMessage = String.format(Messages.MESSAGE_MUST_BE_TRANSACTION_LIST, "findt");
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void toStringMethod() {
        TransactionContainsKeywordsPredicate predicate =
                new TransactionContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindTransactionCommand findTransactionCommand = new FindTransactionCommand(predicate);
        String expected = FindTransactionCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findTransactionCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ClientContainsKeywordsPredicate}.
     */
    private TransactionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TransactionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}


