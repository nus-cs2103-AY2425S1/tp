package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;

/**
 * Imports the contacts from a file to the address book.
 */
public class ImportCommand extends FileAccessCommand {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Address book has been exported!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports the current contacts to a json file with its "
            + "name being the current time and date. ";
    public static final String FILE_DATA_LOAD_ERROR_FORMAT =
            "Could not read data from file %s due to inability to find or access the file.";
    private static final DateTimeFormatter EXPORT_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy-hhmmssa");

    private final Path filePath;

    /**
     * @param filePath of the json file to be imported
     */
    public ImportCommand(Path filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) {
        // This version of execute should never be run
        assert false;
        requireNotExecuted();
        requireNonNull(model);
        isExecuted = true;
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNotExecuted();
        requireNonNull(model);
        isExecuted = true;

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;

        try {
            addressBookOptional = storage.readAddressBook(filePath);
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataLoadingException e) {
            throw new CommandException(String.format(FILE_DATA_LOAD_ERROR_FORMAT, e.getMessage()), e);
        }

        model.setAddressBook(initialData);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //WIP
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        return false;
    }

}