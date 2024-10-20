package seedu.address.logic.commands;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.JsonAddressBookStorage;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.Files.copy;


public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_SUCCESS = "Backup successful";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Path backupPath = Paths.get("backup", "addressbook.json");
        Path originalPath = model.getAddressBookFilePath();
        try {
            FileUtil.createIfMissing(backupPath);
            copy(originalPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
