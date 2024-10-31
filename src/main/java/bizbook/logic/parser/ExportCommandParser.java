package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.Messages.MESSAGE_UNSUPPORTED_FILE_TYPE;
import static bizbook.logic.parser.CliSyntax.PREFIX_FILE;
import static java.util.Objects.requireNonNull;

import bizbook.logic.commands.ExportCommand;
import bizbook.logic.commands.exporter.FileType;
import bizbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new ExportCsvCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCsvCommand
     * and returns a ExportCsvCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExportCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILE);

        if (argMultimap.getValue(PREFIX_FILE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        FileType fileType = ParserUtil.parseFileType(argMultimap.getValue(PREFIX_FILE).get());

        if (!fileType.hasExporter()) {
            throw new ParseException(
                    String.format(MESSAGE_UNSUPPORTED_FILE_TYPE, ExportCommand.MESSAGE_USAGE));
        }

        return new ExportCommand(fileType);
    }
}
