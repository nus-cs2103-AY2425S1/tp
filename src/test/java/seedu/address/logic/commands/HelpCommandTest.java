package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.KeywordCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private final Model model = new ModelManager();
    private final Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "");
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpAdd_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "add");
        assertCommandSuccess(new HelpCommand("add"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpAddf_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "addf");
        assertCommandSuccess(new HelpCommand("addf"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpBookAppt_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "bookappt");
        assertCommandSuccess(new HelpCommand("bookappt"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpClear_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "clear");
        assertCommandSuccess(new HelpCommand("clear"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpDeleteAppt_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "deleteappt");
        assertCommandSuccess(new HelpCommand("deleteappt"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpDelete_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "delete");
        assertCommandSuccess(new HelpCommand("delete"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpEdit_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "edit");
        assertCommandSuccess(new HelpCommand("edit"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpExit_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "exit");
        assertCommandSuccess(new HelpCommand("exit"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpFilter_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "filter");
        assertCommandSuccess(new HelpCommand("filter"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpHome_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "home");
        assertCommandSuccess(new HelpCommand("home"), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_helpView_success() {
        CommandResult expectedCommandResult = new KeywordCommandResult(SHOWING_HELP_MESSAGE, "view");
        assertCommandSuccess(new HelpCommand("view"), model, expectedCommandResult, expectedModel);
    }
}
