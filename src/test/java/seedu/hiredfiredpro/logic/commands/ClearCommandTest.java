package seedu.hiredfiredpro.logic.commands;

import static seedu.hiredfiredpro.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hiredfiredpro.testutil.TypicalPersons.getTypicalHiredFiredPro;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.model.HiredFiredPro;
import seedu.hiredfiredpro.model.Model;
import seedu.hiredfiredpro.model.ModelManager;
import seedu.hiredfiredpro.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyHiredFiredPro_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyHiredFiredPro_success() {
        Model model = new ModelManager(getTypicalHiredFiredPro(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalHiredFiredPro(), new UserPrefs());
        expectedModel.setHiredFiredPro(new HiredFiredPro());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
