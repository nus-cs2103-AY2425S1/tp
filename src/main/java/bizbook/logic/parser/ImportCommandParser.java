package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.Messages.MESSAGE_INVALID_FILE_PATH;
import static bizbook.logic.Messages.MESSAGE_UNSUPPORTED_FILE_TYPE;
import static bizbook.logic.parser.CliSyntax.PREFIX_FILE;
import static bizbook.logic.parser.CliSyntax.PREFIX_PATH;
import static java.util.Objects.requireNonNull;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import bizbook.logic.commands.ImportCommand;
import bizbook.logic.commands.exporter.FileType;
import bizbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns a ImportCsvCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE, PREFIX_PATH);

        if (argMultimap.getValue(PREFIX_FILE).isEmpty()
            || argMultimap.getValue(PREFIX_PATH).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        FileType fileType = ParserUtil.parseFileType(argMultimap.getValue(PREFIX_FILE).get());
        String rawPath = argMultimap.getValue(PREFIX_PATH).get();
        Path path;
        try {
            path = Path.of(rawPath);
        } catch (InvalidPathException ipe) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILE_PATH, rawPath));
        }

        if (!fileType.hasImporter()) {
            throw new ParseException(
                    String.format(MESSAGE_UNSUPPORTED_FILE_TYPE, ImportCommand.MESSAGE_USAGE));
        }

        return new ImportCommand(fileType, path);
    }
}
