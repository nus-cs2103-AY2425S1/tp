package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Placeholder for the import command until we decide to go through with it.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = ":import";
    public static final String MESSAGE_SUCCESS = "Address book has been imported!";
    public static final String COMMAND_SUMMARY_ACTION = "Import";
    public static final String COMMAND_SUMMARY_FORMAT = ":import \n:import --override";
    public static final String COMMAND_SUMMARY_EXAMPLES = ":import\n:import --override";
    private boolean override;

    public ImportCommand(boolean override) {
        this.override = override;
    }
    @Override
    public CommandResult execute(Model model) {
        // TODO: Implement import command
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
