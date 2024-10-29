package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.controller.ConfirmationBypassController;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ConfirmationBypassController confirmationBypassController = new ConfirmationBypassController();

        assertCommandSuccess(new ClearCommand(confirmationBypassController), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_cancelled() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ConfirmationBypassController confirmationBypassController = new ConfirmationBypassController();
        confirmationBypassController.setConfirmationResult(false);

        assertCommandSuccess(new ClearCommand(confirmationBypassController), model,
                ClearCommand.MESSAGE_CLEAR_CANCELLED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        ConfirmationBypassController confirmationBypassController = new ConfirmationBypassController();

        assertCommandSuccess(new ClearCommand(confirmationBypassController), model,
                ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_cancelled() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ConfirmationBypassController confirmationBypassController = new ConfirmationBypassController();
        confirmationBypassController.setConfirmationResult(false);

        assertCommandSuccess(new ClearCommand(confirmationBypassController), model,
                ClearCommand.MESSAGE_CLEAR_CANCELLED, expectedModel);
    }

}
