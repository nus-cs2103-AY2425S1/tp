package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ModCommand.MESSAGE_NOT_IMPLEMENTED_YET;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ModCommandTest {

    private final Model model = new ModelManager();
    @Test
    public void execute() {
        assertCommandFailure(new ModCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }

}
