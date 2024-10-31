package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAgentAssist;

import org.junit.jupiter.api.Test;

import seedu.address.model.AgentAssist;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAgentAssist_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAgentAssist_success() {
        Model model = new ModelManager(getTypicalAgentAssist(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAgentAssist(), new UserPrefs());
        expectedModel.setAgentAssist(new AgentAssist());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
