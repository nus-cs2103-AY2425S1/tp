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

        String expectedConfirmationMessage = Command.CONFIRMATION_MESSAGE;
        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, expectedConfirmationMessage, model);

        // Now we confirm the clear
        ConfirmationYesCommand confirmationCommand = new ConfirmationYesCommand();

        assertCommandSuccess(confirmationCommand, model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedConfirmationMessage = Command.CONFIRMATION_MESSAGE;
        CommandTestUtil.assertCommandSuccess(new ClearCommand(), model, expectedConfirmationMessage, model);

        // Now we confirm the clear
        ConfirmationYesCommand confirmationCommand = new ConfirmationYesCommand();
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(confirmationCommand, model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
