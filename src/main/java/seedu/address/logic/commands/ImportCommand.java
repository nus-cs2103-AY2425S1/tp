package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.Storage;

/**
 * Imports the contacts from a file to the address book.
 */
public class ImportCommand extends FileAccessCommand {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Address book from %s has been imported!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the contacts found in the provided file into the address book. \n"
            + "Parameters: " + PREFIX_FILE_PATH + "FILE_PATH\n"
            + "Example: import " + PREFIX_FILE_PATH + "./data/AY2324S1";
    public static final String FILE_DATA_LOAD_ERROR_FORMAT =
            "Could not read data from file %s due to inability to find or access the file.";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Path filePath;

    /**
     * @param filePath of the json file to be imported
     */
    public ImportCommand(Path filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNotExecuted();
        requireNonNull(model);
        requireNonNull(storage);
        isExecuted = true;

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook importedData;

        try {
            addressBookOptional = storage.readAddressBook(filePath);
            importedData = addressBookOptional.orElseGet(model::getAddressBook);
        } catch (DataLoadingException e) {
            throw new CommandException(String.format(FILE_DATA_LOAD_ERROR_FORMAT, this.filePath.toString()), e);
        }

        model.setAddressBook(importedData);
        logger.info("Successful import from filePath");
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return filePath.equals(otherImportCommand.filePath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("filePath", filePath)
                .toString();
    }
}
