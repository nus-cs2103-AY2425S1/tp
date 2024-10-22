package seedu.address.logic.commands;

import seedu.address.model.Model;

public class CloseCommand extends Command {

    public static final String COMMAND_WORD = "close";

    public static final String WINDOWS_CLOSED_ACKNOWLEDGEMENT = "View window closed.";

    @Override
    public CommandResult execute(Model model) {
        ViewCommand.closeCurrentWindow();
        return new CommandResult(WINDOWS_CLOSED_ACKNOWLEDGEMENT);
    }
}
