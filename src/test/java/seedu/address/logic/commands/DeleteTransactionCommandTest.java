package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Transaction;
public class DeleteTransactionCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_filteredList_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Person personToDeleteFrom = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Transaction> transactions = personToDeleteFrom.getTransactions();
        model.updateTransactionList(transactions);
        List<Transaction> expectedTransactions = new ArrayList<>(transactions);

        Transaction deletedTransaction = expectedTransactions.remove(INDEX_FIRST_TRANSACTION.getZeroBased());

        Person editedPerson = new Person(personToDeleteFrom.getName(), personToDeleteFrom.getCompany(),
                personToDeleteFrom.getPhone(), personToDeleteFrom.getEmail(), personToDeleteFrom.getAddress(),
                personToDeleteFrom.getTags(), expectedTransactions);
        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                Messages.format(deletedTransaction), Messages.format(editedPerson));
        expectedModel.setPerson(personToDeleteFrom, editedPerson);

        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyFilteredTransactionList() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        List<Transaction> transactions = new ArrayList<>();
        model.updateTransactionList(transactions);

        String expectedMessage = Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;

        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        assertCommandFailure(deleteTransactionCommand, model, expectedMessage);
    }

    @Test
    public void execute_unfilteredListFailure() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        String expectedMessage = Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;

        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        assertCommandFailure(deleteTransactionCommand, model, expectedMessage);
    }
    @Test
    public void equals() {
        DeleteTransactionCommand deleteFirstCommand = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        DeleteTransactionCommand deleteSecondCommand = new DeleteTransactionCommand(INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTransactionCommand deleteFirstCommandCopy = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
