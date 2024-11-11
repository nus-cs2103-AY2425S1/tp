package spleetwaise.address.logic.commands;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBook;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.transaction.model.TransactionBook;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.TransactionBookModelManager;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class ClearCommandTest {


    @Test
    public void execute_emptyAddressBook_success() {
        AddressBookModel model = new AddressBookModelManager();
        AddressBookModel expectedModel = new AddressBookModelManager();
        TransactionBookModel tbModel = new TransactionBookModelManager();

        CommonModelManager.initialise(model, tbModel);

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        AddressBookModel model = new AddressBookModelManager(TypicalPersons.getTypicalAddressBook());
        AddressBookModel expectedModel = new AddressBookModelManager(
                TypicalPersons.getTypicalAddressBook());
        expectedModel.setAddressBook(new AddressBook());
        TransactionBookModel tbModel = new TransactionBookModelManager();

        CommonModelManager.initialise(model, tbModel);
        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTransactionBook_success() {
        TransactionBookModel model = new TransactionBookModelManager(TypicalTransactions.getTypicalTransactionBook());
        TransactionBookModel expectedModel = new TransactionBookModelManager(
                TypicalTransactions.getTypicalTransactionBook());
        expectedModel.setTransactionBook(new TransactionBook());

        spleetwaise.transaction.logic.commands.CommandTestUtil.assertCommandSuccess(
                new spleetwaise.transaction.logic.commands.ClearCommand(), model,
                spleetwaise.transaction.logic.commands.ClearCommand.MESSAGE_SUCCESS, expectedModel
        );
    }

}
