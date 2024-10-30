package seedu.eventtory.logic.commands;

import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventtory.testutil.TypicalVendors.getTypicalEventTory;

import org.junit.jupiter.api.Test;

import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.ModelManager;
import seedu.eventtory.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyEventTory_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEventTory_success() {
        Model model = new ModelManager(getTypicalEventTory(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEventTory(), new UserPrefs());
        expectedModel.setEventTory(new EventTory());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
