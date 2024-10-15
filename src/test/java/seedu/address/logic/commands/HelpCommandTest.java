package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_showsHelpWindow() {
        CommandResult commandResult = new HelpCommand().execute(model);
        assert commandResult.isShowHelp() : "Expected help window to be shown";
    }

    // Test to verify SHOWING_HELP_MESSAGE variable
    @Test
    public void showingHelpMessage_isCorrect() {
        String expectedHelpMessage = "Opened help window with link to user guide.";
        assertEquals(expectedHelpMessage, HelpCommand.SHOWING_HELP_MESSAGE,
                "SHOWING_HELP_MESSAGE is not correct");
    }

    @Test
    public void messageUsage_isCorrect() {
        String expectedMessageUsage = "help: Shows program usage instructions.\n"
                + "Example: help";
        assertEquals(expectedMessageUsage, HelpCommand.MESSAGE_USAGE, "MESSAGE_USAGE is not correct");
    }

}
