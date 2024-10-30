package seedu.address.logic.commands.eventcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearEventCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearEventCommand clearEventCommand = new ClearEventCommand();

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearEventCommand.setConfirmed(true);
        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_abort() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearEventCommand clearEventCommand = new ClearEventCommand();

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearEventCommand.setConfirmed(false);
        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_ABORTED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearEventCommand clearEventCommand = new ClearEventCommand();

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearEventCommand.setConfirmed(true);
        expectedModel.setEventList(new AddressBook());

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_abort() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearEventCommand clearEventCommand = new ClearEventCommand();

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearEventCommand.setConfirmed(false);
        expectedModel.setEventList(getTypicalAddressBook());

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_ABORTED, expectedModel);
    }
}
