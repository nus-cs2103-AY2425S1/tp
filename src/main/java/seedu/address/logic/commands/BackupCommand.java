package seedu.address.logic.commands;

import static java.nio.file.Files.copy;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Creates a backup of current storage file.
 */
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_SUCCESS = "Backup successful";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Path backupPath = model.getBackupAddressBookFilePath();
        Path originalPath = model.getAddressBookFilePath();
        backup(originalPath, backupPath);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private void backup(Path originalPath, Path backupPath) throws CommandException {
        try {
            FileUtil.createIfMissing(backupPath);
            copy(originalPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
