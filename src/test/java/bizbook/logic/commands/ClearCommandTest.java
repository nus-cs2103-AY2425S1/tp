package bizbook.logic.commands;

import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import bizbook.model.AddressBook;
import bizbook.model.Model;
import bizbook.model.ModelManager;
import bizbook.model.UserPrefs;

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
