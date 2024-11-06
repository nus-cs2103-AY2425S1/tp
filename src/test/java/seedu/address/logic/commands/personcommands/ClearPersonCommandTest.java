package seedu.address.logic.commands.personcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearPersonCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearPersonCommand clearPersonCommand = new ClearPersonCommand();

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearPersonCommand.setConfirmed(true);
        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_abort() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearPersonCommand clearPersonCommand = new ClearPersonCommand();

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearPersonCommand.setConfirmed(false);
        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_ABORTED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearPersonCommand clearPersonCommand = new ClearPersonCommand();

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearPersonCommand.setConfirmed(true);
        expectedModel.setPersonList(new AddressBook());

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_abort() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearPersonCommand clearPersonCommand = new ClearPersonCommand();

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearPersonCommand.setConfirmed(false);
        expectedModel.setPersonList(getTypicalAddressBook());

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_ABORTED, expectedModel);
    }
}
