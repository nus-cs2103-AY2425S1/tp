package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

//@@author estellelim-reused
//Reused from https://github.com/se-edu/addressbook-level4.git
public class HistoryCommandTest {
    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        assertCommandSuccess(new HistoryCommand(), model, commandHistory, HistoryCommand.MESSAGE_NO_HISTORY,
                expectedModel);

        String command1 = "clear";
        commandHistory.add(command1);
        assertCommandSuccess(new HistoryCommand(), model, commandHistory,
                String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel);

        String command2 = "randomCommand";
        String command3 = "view 1";
        commandHistory.add(command2);
        commandHistory.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));
        assertCommandSuccess(new HistoryCommand(), model, commandHistory, expectedMessage, expectedModel);
    }
}
//@@author
