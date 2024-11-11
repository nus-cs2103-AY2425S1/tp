package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports the current address book data to a chosen file.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "Data has been exported successfully.";
    public static final String MESSAGE_FAILURE = "An error has occurred while exporting the file.";
    public static final String MESSAGE_SELECT_FILE = "Please select the file export data to.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SELECT_FILE, true, success ->
                success ? new CommandResult(MESSAGE_SUCCESS) : new CommandResult(MESSAGE_FAILURE));
    }
}
