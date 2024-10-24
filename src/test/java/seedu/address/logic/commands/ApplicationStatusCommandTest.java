package seedu.address.logic.commands;


import static seedu.address.logic.commands.ApplicationStatusCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;


public class ApplicationStatusCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        assertCommandFailure(new ApplicationStatusCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
