package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Imports address book data from a chosen file.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Data has been imported successfully.";
    public static final String MESSAGE_FAILURE = "An error has occurred while reading the file. "
            + "The chosen file may be corrupted.";
    public static final String MESSAGE_PROMPT = "This will delete ALL data in the current address book. "
            + "Confirm command? (y/n)\n"
            + "You may want to back-up the current data by running 'export' before continuing. ";
    public static final String MESSAGE_SELECT_FILE = "Please select the file import data from.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_PROMPT, () ->
                new CommandResult(MESSAGE_SELECT_FILE, false, success ->
                        success ? new CommandResult(MESSAGE_SUCCESS) : new CommandResult(MESSAGE_FAILURE)));
    }
}
