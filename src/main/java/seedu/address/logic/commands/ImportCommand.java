package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.Storage;

/**
 * Imports the contacts from a file to the address book.
 */
public class ImportCommand extends FileAccessCommand implements Undoable {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Address book from %s has been imported!";
    public static final String MESSAGE_UNDO_SUCCESS = "Address book has been restored to version before import!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the contacts found in the provided file into the address book. \n"
            + "Parameters: " + PREFIX_FILE_PATH + "FILE_PATH\n"
            + "Example: import " + PREFIX_FILE_PATH + "./data/AY2324S1";
    public static final String FILE_DATA_LOAD_ERROR_FORMAT =
            "Could not read data from file %s due to inability to find or access the file.";

    private final Path filePath;
    private AddressBook initialAddressBook;

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

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook importedData;

        initialAddressBook = new AddressBook(model.getAddressBook());

        try {
            addressBookOptional = storage.readAddressBook(filePath);
            importedData = addressBookOptional.orElseGet(model::getAddressBook);
        } catch (DataLoadingException e) {
            throw new CommandException(String.format(FILE_DATA_LOAD_ERROR_FORMAT, this.filePath.toString()), e);
        }

        model.setAddressBook(importedData);
        isExecuted = true;
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.filePath));
    }

    @Override
    public CommandResult undo(Model model) {
        requireExecuted();
        requireNonNull(model);
        requireNonNull(initialAddressBook);
        model.setAddressBook(initialAddressBook);
        isExecuted = false;
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
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
