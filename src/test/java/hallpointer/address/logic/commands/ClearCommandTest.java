package hallpointer.address.logic.commands;

import static hallpointer.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hallpointer.address.testutil.TypicalMembers.getTypicalHallPointer;

import org.junit.jupiter.api.Test;

import hallpointer.address.model.HallPointer;
import hallpointer.address.model.Model;
import hallpointer.address.model.ModelManager;
import hallpointer.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyHallPointer_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyHallPointer_success() {
        Model model = new ModelManager(getTypicalHallPointer(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalHallPointer(), new UserPrefs());
        expectedModel.setHallPointer(new HallPointer());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
