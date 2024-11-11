package spleetwaise.transaction.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.testutil.TypicalIndexes;
import spleetwaise.transaction.testutil.TypicalTransactions;

/**
 * Contains integration tests (interaction with the CommonModel) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private TransactionBookModel model;
    private TransactionBookModel expectedModel;

    @BeforeEach
    public void setUp() {
        model = new TransactionBookModelManager(TypicalTransactions.getTypicalTransactionBook());
        expectedModel = new TransactionBookModelManager(model.getTransactionBook());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandTestUtil.assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        CommandTestUtil.showTransactionAtIndex(model, TypicalIndexes.INDEX_FIRST_TRANSACTION);
        CommandTestUtil.assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
