package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ExportCommand extends Command {
    public static final String MESSAGE_ARGUMENTS = "Export: %1$s";

    private final String format;

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book in CSV format. "
            + "Example: " + COMMAND_WORD + " "
            + "format/csv";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET =
            "Export command not implemented yet";

    public ExportCommand(String format) {
        requireAllNonNull(format);

        this.format = format;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, format));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand e = (ExportCommand) other;
        return format.equals(e.format);
    }
}
