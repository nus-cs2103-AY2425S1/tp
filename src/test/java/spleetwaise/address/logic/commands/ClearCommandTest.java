package spleetwaise.address.logic.commands;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.AddressBook;
import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.AddressBookModelManager;
import spleetwaise.address.model.UserPrefs;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.model.CommonModel;

public class ClearCommandTest {


    @Test
    public void execute_emptyAddressBook_success() {
        AddressBookModel model = new AddressBookModelManager();
        AddressBookModel expectedModel = new AddressBookModelManager();

        CommonModel.initialise(model, null);

        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        AddressBookModel model = new AddressBookModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        AddressBookModel expectedModel = new AddressBookModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        CommonModel.initialise(model, null);
        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
