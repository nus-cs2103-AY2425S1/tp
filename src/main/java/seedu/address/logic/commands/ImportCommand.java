package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Imports JSON file as addressbook data.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports a json file if it exists in the same "
            + "directory as MediContact.jar and the data "
            + "is in the correct format (refer to user guide linked in help window for more information).\n"
            + "Parameters: FILE_NAME.json\n"
            + "Example: " + COMMAND_WORD + " addressbook.json ";
    public static final String MESSAGE_IMPORT_SUCCESS = "Data successfully imported into MediContact";
    public static final String MESSAGE_IMPORT_FAIL = "Error reading or writing '%1$s'";
    public static final String MESSAGE_FILE_NOT_FOUND = "'%1$s' not found in the same directory as the "
            + "application JAR file";
    public static final String MESSAGE_FILE_NOT_JSON = "'%1$s' is not a JSON file";
    public static final String MESSAGE_FILE_FORMAT_FAIL_NO_PERSONS = "Invalid file format: 'persons' array is missing";
    public static final String MESSAGE_FILE_FORMAT_FAIL_INVALID_JSON = "Invalid file format: invalid JSON format";
    public static final String MESSAGE_FILE_FORMAT_FAIL_INVALID_FORMAT = "Invalid file format: data does not follow "
            + "the correct format";
    public static final String MESSAGE_FILE_FORMAT_FAIL_INVALID_OPTIONAL = "Invalid file format: '%1$s' should be an "
            + "array or null";
    private final String fileContent;

    public ImportCommand(String fileContent) {
        this.fileContent = fileContent;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(MESSAGE_IMPORT_SUCCESS);
    }

    public String getFileContent() {
        return this.fileContent;
    }
}
