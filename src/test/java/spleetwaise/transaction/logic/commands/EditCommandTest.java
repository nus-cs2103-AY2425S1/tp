package spleetwaise.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.address.commons.core.index.Index;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.UserPrefs;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.logic.Messages;
import spleetwaise.transaction.logic.commands.EditCommand.EditTransactionDescriptor;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.testutil.TransactionBuilder;
import spleetwaise.transaction.testutil.TypicalIndexes;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class EditCommandTest {
    private final TransactionBookModel tbModel = new TransactionBookModelManager(
            TypicalTransactions.getTypicalTransactionBook());
    private final AddressBookModel abModel = new AddressBookModelManager(
            TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    void setUp() {
        CommonModel.initialise(abModel, tbModel);
    }

    @Test
    public void execute_validEditTransaction_success() {
        Transaction editedTransaction = new TransactionBuilder().build();
        EditTransactionDescriptor descriptor = new EditTransactionDescriptor(buildDescriptor(editedTransaction));
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TXN_SUCCESS, editedTransaction);

        TransactionBookModel expectedModel = new TransactionBookModelManager(tbModel.getTransactionBook());
        expectedModel.setTransaction(
                expectedModel.getFilteredTransactionList().get(TypicalIndexes.INDEX_FIRST_TRANSACTION.getZeroBased()),
                editedTransaction
        );

        CommandTestUtil.assertCommandSuccess(editCommand, tbModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyEditTransaction_success() {
        EditTransactionDescriptor descriptor = new EditTransactionDescriptor();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, descriptor);

        String expectedMessage = String.format(
                EditCommand.MESSAGE_EDIT_TXN_SUCCESS,
                tbModel.getFilteredTransactionList().get(0)
        );

        TransactionBookModel expectedModel = new TransactionBookModelManager(tbModel.getTransactionBook());

        CommandTestUtil.assertCommandSuccess(editCommand, tbModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEditTransaction_failure() {
        Transaction editedTransaction =
                tbModel.getFilteredTransactionList().get(TypicalIndexes.INDEX_SECOND_TRANSACTION.getZeroBased());

        EditTransactionDescriptor descriptor = new EditTransactionDescriptor(buildDescriptor(editedTransaction));
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_DUPLICATE_TXN, editedTransaction);

        CommandTestUtil.assertCommandFailure(editCommand, tbModel, expectedMessage);
    }

    @Test
    public void execute_invalidIndexEditTransaction_failure() {
        Index invalidIndex = Index.fromZeroBased(tbModel.getFilteredTransactionList().size());
        EditTransactionDescriptor descriptor = new EditTransactionDescriptor();

        EditCommand editCommand = new EditCommand(invalidIndex, descriptor);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);

        CommandTestUtil.assertCommandFailure(editCommand, tbModel, expectedMessage);
    }

    @Test
    public void execute_invalidFilteredListIndexEditTransaction_failure() {
        CommandTestUtil.showTransactionAtIndex(tbModel, TypicalIndexes.INDEX_FIRST_TRANSACTION);

        Index invalidIndex = TypicalIndexes.INDEX_SECOND_TRANSACTION;
        assert invalidIndex.getZeroBased() < tbModel.getTransactionBook().getTransactionList().size();

        EditTransactionDescriptor descriptor = new EditTransactionDescriptor();

        EditCommand editCommand = new EditCommand(invalidIndex, descriptor);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);

        CommandTestUtil.assertCommandFailure(editCommand, tbModel, expectedMessage);
    }

    @Test
    public void equals() {
        final EditTransactionDescriptor someDescriptor = new EditTransactionDescriptor();
        someDescriptor.setDescription(new Description("foo"));
        final EditCommand someEditCommand = new EditCommand(
                TypicalIndexes.INDEX_FIRST_TRANSACTION, someDescriptor);

        // same values -> true
        EditTransactionDescriptor clonedDescriptor = new EditTransactionDescriptor(someEditCommand.getDescriptor());
        EditCommand anotherCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, clonedDescriptor);
        assertEquals(someEditCommand, anotherCommand);

        // same object -> true
        assertEquals(someEditCommand, someEditCommand);

        // null -> false
        assertNotEquals(null, someEditCommand);

        // different type -> false
        assertNotEquals(someEditCommand, new ListCommand());

        // different index -> false
        assertNotEquals(someEditCommand, new EditCommand(TypicalIndexes.INDEX_SECOND_TRANSACTION, clonedDescriptor));

        // different descriptor -> false
        clonedDescriptor.setDescription(new Description("foo2"));
        assertNotEquals(someEditCommand, new EditCommand(TypicalIndexes.INDEX_FIRST_TRANSACTION, clonedDescriptor));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditTransactionDescriptor
                descriptor = new EditTransactionDescriptor();
        EditCommand editCommand = new EditCommand(index, descriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index
                + ", editTransactionDescriptor="
                + descriptor
                + "}";
        assertEquals(expected, editCommand.toString());
    }

    private EditTransactionDescriptor buildDescriptor(Transaction transactionToEdit) {
        EditTransactionDescriptor descriptor = new EditTransactionDescriptor();
        descriptor.setId(transactionToEdit.getId());
        descriptor.setPerson(transactionToEdit.getPerson());
        descriptor.setAmount(transactionToEdit.getAmount());
        descriptor.setDescription(transactionToEdit.getDescription());
        descriptor.setDate(transactionToEdit.getDate());
        descriptor.setStatus(transactionToEdit.getStatus());
        descriptor.setCategories(transactionToEdit.getCategories());

        return descriptor;
    }

}
