package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;

import java.io.IOException;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.exporter.Exporter;
import seedu.address.logic.exporter.FileType;
import seedu.address.model.Model;

/**
 * Exports contact list to a csv file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the whole contact list "
            + "to a specified file type. Only CSV and VCF are supported.\n"
            + "Parameters: " + PREFIX_FILE + "FILETYPE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILE + "csv";

    public static final String MESSAGE_SUCCESS = "Contact list successfully exported to a %1$s file";
    public static final String MESSAGE_FAILURE = "Unable to export contact list to a %1$s file"
            + ", please ensure that the file is closed before exporting";

    private final FileType fileType;

    /**
     * Creates an ExportCsvCommand.
     *
     * @param fileType to export the data into.
     */
    public ExportCommand(FileType fileType) {
        requireNonNull(fileType);
        this.fileType = fileType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Exporter exporter = fileType.export(model.getUserPrefs());
            exporter.exportAddressBook(model.getAddressBook());
        } catch (IOException io) {
            throw new CommandException(String.format(MESSAGE_FAILURE, fileType));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileType));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

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
