package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    public static final String MESSAGE_INVALID_FILE_PATH = "The specified file path is invalid: %s";

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Check if the argument (file path) is empty
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        // Validate the file path format
        if (!isValidFilePath(trimmedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILE_PATH, trimmedArgs));
        }

        // Return the ImportCommand with the file path as an argument
        return new ImportCommand(trimmedArgs);
    }

    /**
     * Validates whether a given string is a valid file path.
     *
     * @param filePath The file path string to validate.
     * @return true if the file path is valid, false otherwise.
     */
    private boolean isValidFilePath(String filePath) {
        // Define some characters that are invalid for file paths
        String invalidChars = "*<>?|";

        for (char ch : invalidChars.toCharArray()) {
            if (filePath.contains(Character.toString(ch))) {
                return false;
            }
        }

        try {
            // Check if it's a valid path by attempting to create a Path object
            Paths.get(filePath);
            if (filePath.contains("//")) {
                return false;
            }
            return true;
        } catch (InvalidPathException e) {
            return false;
        }
    }

}
