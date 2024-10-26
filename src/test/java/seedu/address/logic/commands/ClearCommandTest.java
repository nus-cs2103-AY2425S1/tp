package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearCommandTest {

    @Test
    public void execute_clearPromptsConfirmationCheck_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearCommand.setIsClear(false);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CHECK, expectedModel);
        assertTrue(ClearCommand.getIsClear());
    }

}
