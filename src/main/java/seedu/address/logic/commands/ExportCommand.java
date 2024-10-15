package seedu.address.logic.commands;

import seedu.address.model.Model;

public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = "wip";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_USAGE);
    }
}
