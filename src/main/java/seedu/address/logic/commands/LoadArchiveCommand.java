package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * Loads an archive file and sets it as the current address book.
 */
public class LoadArchiveCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(LoadArchiveCommand.class);

    public static final String COMMAND_WORD = "loadArchive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads an archive file.\n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD + "addressbook-20241023_114324-example.json";

    public static final String MESSAGE_SUCCESS = "Loaded archive file: %1$s";
    public static final String MESSAGE_NOT_FOUND = "Archive file not found: %1$s";
    public static final String MESSAGE_FAILURE = "Failed to load archive file: %1$s";

    private final String archiveFilename;

    public LoadArchiveCommand(String archiveFilename) {
        this.archiveFilename = archiveFilename;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Path source = model.getAddressBookFilePath();
        assert source != null : "Address book file path is null";

        Path archiveFile = Paths.get(source.getParent().toString(), "archive", archiveFilename);
        if (!Files.exists(archiveFile)) {
            return new CommandResult(String.format(MESSAGE_NOT_FOUND, archiveFilename));
        }

        try {
            JsonAddressBookStorage storage = new JsonAddressBookStorage(archiveFile);
            ReadOnlyAddressBook addressBook = storage.readAddressBook().orElseThrow(IOException::new);
            model.setAddressBook(addressBook);
        } catch (IOException | DataLoadingException e) {
            logger.severe("Failed to load archive file: " + e.getMessage());
            return new CommandResult(String.format(MESSAGE_FAILURE, archiveFilename));
        }

        logger.info("Loaded archive file: " + archiveFilename);
        return new CommandResult(String.format(MESSAGE_SUCCESS, archiveFilename));
    }
}
