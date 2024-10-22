package spleetwaise.transaction.logic.commands;

import org.junit.jupiter.api.Test;

import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.model.TransactionBook;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class ClearCommandTest {

    @Test
    public void execute_emptyTransactionBook_success() {
        TransactionBookModel model = new TransactionBookModelManager();
        TransactionBookModel expectedModel = new TransactionBookModelManager();

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTransactionBook_success() {
        TransactionBookModel model = new TransactionBookModelManager(TypicalTransactions.getTypicalTransactionBook());
        TransactionBookModel expectedModel = new TransactionBookModelManager(TypicalTransactions.getTypicalTransactionBook());
        expectedModel.setTransactionBook(new TransactionBook());

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
