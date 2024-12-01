package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.filename.Filename;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes an archive file.
 */
public class DeleteArchiveCommand extends Command {
    public static final String COMMAND_WORD = "deletearchive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an archive file.\n\n"
            + "Parameters: FILENAME\n\n"
            + "Example: " + COMMAND_WORD + " addressbook-2024-11-06T20-29-05.7609475-example.json";

    public static final String MESSAGE_SUCCESS = "Deleted archive file: %1$s";
    public static final String MESSAGE_NOT_FOUND = "Archive file not found: %1$s";
    public static final String MESSAGE_FAILURE = "Failed to delete archive file: %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteArchiveCommand.class);

    private final Filename archiveFilename;

    public DeleteArchiveCommand(Filename archiveFilename) {
        this.archiveFilename = archiveFilename;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Path archiveFile = Paths.get(model.getArchiveDirectoryPath().toString(), archiveFilename.toString());
        if (!FileUtil.isFileExists(archiveFile)) {
            logger.info("Archive file not found: " + archiveFilename);
            throw new CommandException(String.format(MESSAGE_NOT_FOUND, archiveFilename));
        }

        try {
            Files.deleteIfExists(archiveFile);
        } catch (IOException e) {
            logger.severe("Failed to delete archive file: " + e.getMessage());
            throw new CommandException(String.format(MESSAGE_FAILURE, archiveFilename));
        }

        logger.info("Deleted archive file: " + archiveFilename);
        return new CommandResult(String.format(MESSAGE_SUCCESS, archiveFilename));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteArchiveCommand)) {
            return false;
        }

        DeleteArchiveCommand otherDeleteCommand = (DeleteArchiveCommand) other;
        return archiveFilename.equals(otherDeleteCommand.archiveFilename);
    }
}
