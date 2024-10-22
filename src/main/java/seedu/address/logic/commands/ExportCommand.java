package seedu.address.logic.commands;

import seedu.address.model.Model;

import java.io.File;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

/**
 * Exports addressbook.json into user's local device.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_EXPORT_ACKNOWLEDGEMENT = "Exporting addressbook.json under "
            + "file path data/addressbook.json in whichever folder addressbook.jar is in";

    public static final String MESSAGE_EXPORT_FAIL = "Export failed!";
    private static final String ADDRESSBOOK_FILE_PATH = "data/addressbook.json";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        try {
            File file = new File(ADDRESSBOOK_FILE_PATH);
            File dir = file.getParentFile();

            // if directory does not exist, make it
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }

            // if file doesn't exist, make it
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_EXPORT_FAIL);
        }

        return new CommandResult(MESSAGE_EXPORT_ACKNOWLEDGEMENT);
    }

}

