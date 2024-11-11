package seedu.sellsavvy.logic.commands.generalcommands;

import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.model.AddressBook;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
