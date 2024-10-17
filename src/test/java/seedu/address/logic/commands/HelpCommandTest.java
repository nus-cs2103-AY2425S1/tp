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
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "", true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_add_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "add", true, false);
        assertCommandSuccess(new HelpCommand("add"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_addf_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "addf", true, false);
        assertCommandSuccess(new HelpCommand("addf"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_appt_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "appt", true, false);
        assertCommandSuccess(new HelpCommand("appt"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_delete_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "delete", true, false);
        assertCommandSuccess(new HelpCommand("delete"), model, expectedCommandResult, expectedModel);
    }
}
