package bizbook.logic.commands;

import static bizbook.logic.parser.CliSyntax.PREFIX_FILE;
import static bizbook.logic.parser.CliSyntax.PREFIX_PATH;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import bizbook.commons.util.ToStringBuilder;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.logic.commands.exporter.FileType;
import bizbook.logic.commands.exporter.Importer;
import bizbook.logic.commands.exporter.exceptions.InvalidFileException;
import bizbook.model.Model;

/**
 * Imports contact list to a csv file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from a file into the address book."
            + " Only VCF is supported.\n"
            + "Parameters: " + PREFIX_FILE + "FILETYPE " + PREFIX_PATH + "PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILE + "vcf "
            + PREFIX_PATH + "./path/to/contacts.vcf";

    public static final String MESSAGE_SUCCESS = "%1$d contacts were successfully imported from file";
    public static final String MESSAGE_FAILED_TO_LOAD = "Unable to find or load the file to import the contact list";

    private final FileType fileType;
    private final Path path;

    /**
     * Creates an ImportCommand.
     *
     * @param fileType of the file at the path.
     * @param path to import the data from.
     */
    public ImportCommand(FileType fileType, Path path) {
        requireNonNull(fileType);
        requireNonNull(path);

        this.fileType = fileType;
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Importer importer = fileType.importer();
            model.setAddressBook(importer.importAddressBook(path));
        } catch (IOException io) {
            throw new CommandException(String.format(MESSAGE_FAILED_TO_LOAD));
        } catch (InvalidFileException ife) {
            throw new CommandException(ife.getMessage());
        }

        int numContacts = model.getAddressBook().getPersonList().size();
        model.getFocusedPerson().set(null);
        return new CommandResult(String.format(MESSAGE_SUCCESS, numContacts));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherExportCommand = (ImportCommand) other;
        return fileType.equals(otherExportCommand.fileType)
                && path.equals(otherExportCommand.path);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("fileType", fileType)
                .add("path", path)
                .toString();
    }
}
