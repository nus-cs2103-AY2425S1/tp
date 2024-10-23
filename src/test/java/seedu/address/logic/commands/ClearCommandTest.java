package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalCampusConnect;

import org.junit.jupiter.api.Test;

import seedu.address.model.CampusConnect;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyCampusConnect_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCampusConnect_success() {
        Model model = new ModelManager(getTypicalCampusConnect(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCampusConnect(), new UserPrefs());
        expectedModel.setCampusConnect(new CampusConnect());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ClearCommand c1 = new ClearCommand();
        ClearCommand c2 = new ClearCommand();

        assertTrue(c1.equals(c2));
    }

}
