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
    public void execute_helpBookAppt_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "bookappt",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("bookappt"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpClear_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "clear",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("clear"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpDeleteAppt_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "deleteappt",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("deleteappt"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpDelete_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "delete",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("delete"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpEdit_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "edit",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("edit"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpExit_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "exit",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("exit"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpFilter_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "filter",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("filter"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpHome_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "home",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("home"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpView_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, "view",
                true, null, false, false);
        assertCommandSuccess(new HelpCommand("view"), model, expectedCommandResult, expectedModel);
    }
}
