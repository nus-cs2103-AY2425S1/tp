package careconnect.logic.commands;

import static careconnect.logic.commands.CommandTestUtil.assertCommandSuccess;
import static careconnect.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import careconnect.model.AddressBook;
import careconnect.model.Model;
import careconnect.model.ModelManager;
import careconnect.model.UserPrefs;

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
