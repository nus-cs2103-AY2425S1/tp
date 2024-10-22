package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;

import seedu.address.model.Model;

/**
 * Exports addressbook.json into user's local device.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_EXPORT_ACKNOWLEDGEMENT = "Exporting MediContact data into "
            + "addressbook.json under file path data/addressbook.json in the current folder of addressbook.jar";
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
            throw new RuntimeException(e);
        }

        return new CommandResult(MESSAGE_EXPORT_ACKNOWLEDGEMENT);
    }

}

