package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public ExportCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Tokenize the input arguments
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_PATH);

        // Check that the required prefix is present
        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        // Get the file path
        String filePath = argMultimap.getValue(CliSyntax.PREFIX_PATH).orElse("").trim();
        if (filePath.isEmpty()) {
            throw new ParseException("File path cannot be empty.");
        }

        return new ExportCommand(filePath);
    }
}
