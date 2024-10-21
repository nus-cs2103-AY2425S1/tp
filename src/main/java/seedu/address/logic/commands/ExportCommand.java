package seedu.address.logic.commands;

import seedu.address.model.Model;

public class ExportCommand extends Command {
    public static final String COMMAND_WORD = ":export";
    public static final String MESSAGE_SUCCESS = "Address book has been exported!";
    public static final String COMMAND_SUMMARY_ACTION = "Export";
    public static final String COMMAND_SUMMARY_FORMAT = ":export";
    public static final String COMMAND_SUMMARY_EXAMPLES = ":export";

    @Override
    public CommandResult execute(Model model) {
        // TODO: Implement export command
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
