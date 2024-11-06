package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ConfirmClearCommandTest {

    @Test
    public void execute_clearPromptsConfirmationCheck_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ConfirmClearCommand.setIsClear(false);

        assertCommandSuccess(new ConfirmClearCommand(), model, ConfirmClearCommand.MESSAGE_CHECK, expectedModel);
        assertTrue(ConfirmClearCommand.getIsClear());
    }

}
