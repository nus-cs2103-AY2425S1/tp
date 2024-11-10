package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Exports the current address book to a .csv file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_EXPORT_ACKNOWLEDGEMENT = "Client contacts have been exported!";
    public static final String HELP_EXPORT_COMMAND = "Export Command\n"
            + "- Format: export\n"
            + "- Example: export\n"
            + "- Exports the current client contacts in PROperty to a Comma-seperated Value (CSV) file format\n"
            + "- By default, the exported CSV file will be located in the \"data\" folder with the"
            + " name \"property.csv\"";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_EXPORT_ACKNOWLEDGEMENT, false, false, true);
    }

}
