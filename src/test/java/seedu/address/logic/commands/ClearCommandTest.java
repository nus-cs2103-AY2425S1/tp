package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearCommand clearCommand = new ClearCommand();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearCommand.setConfirmed(true);
        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_abort() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearCommand clearCommand = new ClearCommand();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearCommand.setConfirmed(false);
        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_ABORTED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearCommand clearCommand = new ClearCommand();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearCommand.setConfirmed(true);
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_abort() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearCommand clearCommand = new ClearCommand();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearCommand.setConfirmed(false);
        expectedModel.setAddressBook(getTypicalAddressBook());

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_ABORTED, expectedModel);
    }
}
