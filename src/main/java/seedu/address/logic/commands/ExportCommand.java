package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports contact list to a csv format.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the whole contact list"
            + "to a specfied file type.\n"
            + "Parameters: " + "FILETYPE\n"
            + "Example: " + COMMAND_WORD + " f/csv";

    public static final String MESSAGE_SUCCESS = "Contact list exported to a %1$s file";

    private final String fileType;

    /**
     * Creates an ExportCsvCommand.
     *
     * @param fileType The file type to export the data into.
     */
    public ExportCommand(String fileType) {
        requireNonNull(fileType);
        this.fileType = fileType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> list = model.getFilteredPersonList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileType));
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

        ExportCommand otherExportCommand = (ExportCommand) other;
        return fileType.equals(otherExportCommand.fileType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("fileType", fileType)
                .toString();
    }
}
