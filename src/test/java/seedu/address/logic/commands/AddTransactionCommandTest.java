package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OTHER_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showEmptyClientList;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.Transaction;
import seedu.address.model.client.TransactionDateComparator;

public class AddTransactionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Transaction transactionToAdd = new Transaction(VALID_DESCRIPTION, VALID_AMOUNT, VALID_OTHER_PARTY,
            VALID_DATE);

    @Test
    public void constructor_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTransactionCommand(INDEX_FIRST_CLIENT, null));
    }

    @Test
    public void execute_filteredList_success() {

        showClientAtIndex(model, INDEX_FIRST_CLIENT);
        Client selectedClient = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(INDEX_FIRST_CLIENT, transactionToAdd);

        List<Transaction> transactions = new ArrayList<>(selectedClient.getTransactions());
        transactions.add(transactionToAdd);
        transactions.sort(new TransactionDateComparator());

        Client editedClient = new Client(selectedClient.getName(), selectedClient.getCompany(),
                selectedClient.getPhone(), selectedClient.getEmail(), selectedClient.getAddress(),
                selectedClient.getTags(), transactions);

        String expectedMessage = String.format(AddTransactionCommand.MESSAGE_ADD_TRANSACTION_SUCCESS,
                Messages.format(transactionToAdd), Messages.format(editedClient));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setClient(selectedClient, editedClient);
        showClientAtIndex(expectedModel, INDEX_FIRST_CLIENT);

        assertCommandSuccess(addTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredList_success() {

        Client selectedClient = model.getFilteredClientList().get(INDEX_FIRST_CLIENT.getZeroBased());

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(INDEX_FIRST_CLIENT, transactionToAdd);

        List<Transaction> transactions = new ArrayList<>(selectedClient.getTransactions());
        transactions.add(transactionToAdd);
        transactions.sort(new TransactionDateComparator());

        Client editedClient = new Client(selectedClient.getName(), selectedClient.getCompany(),
                selectedClient.getPhone(), selectedClient.getEmail(), selectedClient.getAddress(),
                selectedClient.getTags(), transactions);

        String expectedMessage = String.format(AddTransactionCommand.MESSAGE_ADD_TRANSACTION_SUCCESS,
                Messages.format(transactionToAdd), Messages.format(editedClient));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setClient(selectedClient, editedClient);

        assertCommandSuccess(addTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_throwCommandException() {
        showEmptyClientList(model);

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(INDEX_FIRST_CLIENT, transactionToAdd);

        String expectedMessage = String.format(Messages.MESSAGE_EMPTY_CLIENT_LIST, AddTransactionCommand.COMMAND_WORD);

        assertCommandFailure(addTransactionCommand, model, expectedMessage);

    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {

        showClientAtIndex(model, INDEX_FIRST_CLIENT);

        Index outOfBoundIndex = INDEX_SECOND_CLIENT;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(outOfBoundIndex, transactionToAdd);

        assertCommandFailure(addTransactionCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(outOfBoundIndex, transactionToAdd);

        assertCommandFailure(addTransactionCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_transactionListView_throwsCommandException() {

        AddTransactionCommand addTransactionCommand = new AddTransactionCommand(INDEX_FIRST_CLIENT, transactionToAdd);
        model.setIsViewTransactions(true);

        String expectedMessage = String.format(Messages.MESSAGE_MUST_BE_CLIENT_LIST,
                AddTransactionCommand.COMMAND_WORD);
        assertCommandFailure(addTransactionCommand, model, expectedMessage);
    }

    @Test
    public void execute_causeDoubleOverflow_throwsCommandException() {
        // equals negative infinity
        Transaction transactionToAdd1 = new Transaction(VALID_DESCRIPTION, Double.NEGATIVE_INFINITY,
                VALID_OTHER_PARTY, VALID_DATE);
        AddTransactionCommand addTransactionCommand1 = new AddTransactionCommand(INDEX_FIRST_CLIENT, transactionToAdd1);
        assertCommandFailure(addTransactionCommand1, model, Messages.MESSAGE_DOUBLE_OVERFLOW);

        // equals positive infinity
        Transaction transactionToAdd2 = new Transaction(VALID_DESCRIPTION, Double.POSITIVE_INFINITY,
                VALID_OTHER_PARTY, VALID_DATE);
        AddTransactionCommand addTransactionCommand2 = new AddTransactionCommand(INDEX_FIRST_CLIENT, transactionToAdd2);
        assertCommandFailure(addTransactionCommand2, model, Messages.MESSAGE_DOUBLE_OVERFLOW);
    }

    @Test
    public void equals() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Transaction t1 = new Transaction("buy raw materials", -100,
                "Company ABC", LocalDate.parse("2024-10-15", DateTimeUtil.DEFAULT_DATE_PARSER));
        Transaction t2 = new Transaction("sell raw materials", 200,
                "Company XYZ", LocalDate.parse("2024-10-16", DateTimeUtil.DEFAULT_DATE_PARSER));

        AddTransactionCommand addTransactionCommand1 = new AddTransactionCommand(INDEX_FIRST_CLIENT, t1);
        AddTransactionCommand addTransactionCommand2 = new AddTransactionCommand(INDEX_FIRST_CLIENT, t2);
        AddTransactionCommand addTransactionCommand3 = new AddTransactionCommand(INDEX_SECOND_CLIENT, t1);

        // same object -> returns true
        assertTrue(addTransactionCommand1.equals(addTransactionCommand1));

        // same values -> returns true
        AddTransactionCommand addT1CommandCopy = new AddTransactionCommand(INDEX_FIRST_CLIENT, t1);
        assertTrue(addTransactionCommand1.equals(addT1CommandCopy));

        // different types -> returns false
        assertFalse(addTransactionCommand1.equals(1));

        // null -> returns false
        assertFalse(addTransactionCommand1.equals(null));

        // different transaction -> returns false
        assertFalse(addTransactionCommand1.equals(addTransactionCommand2));

        //different index -> returns false
        assertFalse(addTransactionCommand1.equals(addTransactionCommand3));
    }

}
