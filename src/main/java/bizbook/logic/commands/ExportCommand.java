package bizbook.logic.commands;

import static bizbook.logic.parser.CliSyntax.PREFIX_FILE;
import static java.util.Objects.requireNonNull;

import java.io.IOException;

import bizbook.commons.util.ToStringBuilder;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.logic.commands.exporter.Exporter;
import bizbook.logic.commands.exporter.FileType;
import bizbook.logic.commands.exporter.exceptions.InvalidAddressBookException;
import bizbook.model.Model;

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
    public static final String MESSAGE_FAILED_TO_SAVE = "Unable to export contact list to a %1$s file"
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
            throw new CommandException(String.format(MESSAGE_FAILED_TO_SAVE, fileType));
        } catch (InvalidAddressBookException iabe) {
            throw new CommandException(iabe.getMessage());
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
