package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;

public class SeedCommandTest {

    private Model model;

    @Test
    public void execute() {
        assertCommandFailure(new SeedCommand(), model, SeedCommand.MESSAGE_NOT_IMPLEMENTED_YET);
    }

}
