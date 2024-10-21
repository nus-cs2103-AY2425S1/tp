package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports contact list to a csv format.
 */
public class ExportCsvCommand extends Command {

    public static final String COMMAND_WORD = "exportcsv";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the whole contact list to a csv file. "
            + "Parameters: " + "FILENAME"
            + "Example: " + COMMAND_WORD + " "
            + "exported.csv";

    public static final String MESSAGE_SUCCESS = "Contact list exported to: %1$s";

    private final String toFile;

    /**
     * Creates an ExportCsvCommand.
     *
     * @param fileName
     */
    public ExportCsvCommand(String fileName) {
        requireNonNull(fileName);
        toFile = fileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toFile));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCsvCommand)) {
            return false;
        }

        ExportCsvCommand otherExportCsvCommand = (ExportCsvCommand) other;
        return toFile.equals(otherExportCsvCommand.toFile);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toFile", toFile)
                .toString();
    }
}
