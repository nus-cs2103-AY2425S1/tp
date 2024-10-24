package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpAdd_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "add",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("add"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpAddf_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "addf",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("addf"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpAppt_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "appt",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("appt"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpDelete_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "delete",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("delete"), model, expectedCommandResult, expectedModel);
    }
}
