package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n";
    private static final String MESSAGE_INVALID_PATH = "Invalid path!\n";

    /**
     * Parses the given import path as {@code String} and translates it into a Path object
     * and returns an ImportCommand object for execution.
     * @throws ParseException
     */
    public ImportCommand parse(String args) throws ParseException {
        if (args.isBlank() || args.trim().isEmpty()) {
            throw new ParseException(
                String.format("%s\n%s", MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        requireNonNull(args);
        Path path = Paths.get(args.trim());
        if (!Files.exists(path)) {
            throw new ParseException(
                String.format("%s\n%s", MESSAGE_INVALID_PATH, ImportCommand.MESSAGE_USAGE));
        }
        return new ImportCommand(path);

    }
}
