package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.LogicManager.FILE_OPS_ERROR_FORMAT;
import static seedu.address.logic.LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Exports the contacts to a file akin to the save file.
 */
public class ExportCommand extends FileAccessCommand {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "Address book has been exported!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Exports the current contacts to a json file with its "
        + "name being the current time and date. ";
    private static final DateTimeFormatter EXPORT_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy-hhmmssa");

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireNotExecuted();
        requireNonNull(model);
        isExecuted = true;

        LocalDateTime now = LocalDateTime.now();
        Path exportFileLocation = Paths.get("./data/" + now.format(EXPORT_FORMATTER) + ".json");

        try {
            FileUtil.createIfMissing(exportFileLocation);
            storage.saveAddressBook(model.getAddressBook(), exportFileLocation);
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

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
