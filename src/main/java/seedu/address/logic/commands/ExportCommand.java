package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports the address book in a specified format.
 * Currently, BA€ can export the address book as a CSV file.
 */
public class ExportCommand extends Command {
    public static final String MESSAGE_ARGUMENTS = "Export: %1$s";

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book in CSV format. "
            + "Example: " + COMMAND_WORD + " "
            + "format/csv";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Export command not implemented yet";
    private final String format;

    /**
     * Constructs a ExportCommand instance (TODO: supplement JavaDoc stub)
     * @param format the file format of the file to be exported (this should be ".csv")
     */
    public ExportCommand(String format) {
        requireAllNonNull(format);
        this.format = format;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, format));
    }
}
