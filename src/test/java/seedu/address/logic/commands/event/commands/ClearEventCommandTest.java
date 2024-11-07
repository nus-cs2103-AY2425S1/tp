package seedu.address.logic.commands.event.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventManager;

public class ClearEventCommandTest {
    @Test
    public void execute_emptyEventManager_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearEventCommand(), model, ClearEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());
        expectedModel.setEventManager(new EventManager());

        assertCommandSuccess(new ClearEventCommand(), model, ClearEventCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
