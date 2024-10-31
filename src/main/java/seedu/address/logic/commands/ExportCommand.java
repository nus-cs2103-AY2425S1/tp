package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Exports the current address book to a .csv file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_EXPORT_ACKNOWLEDGEMENT = "Address book data has been exported!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_EXPORT_ACKNOWLEDGEMENT, false, false, true);
    }

}
