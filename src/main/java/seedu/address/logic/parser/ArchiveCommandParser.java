package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.nio.file.Path;
import java.util.stream.Stream;

import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ArchiveCommand object
 */
public class ArchiveCommandParser implements Parser<ArchiveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ArchiveCommand
     * and returns an ArchiveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ArchiveCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_PATH)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
        }
        Path path = ParserUtil.parsePathWithoutCheck(argMultimap.getValue(PREFIX_PATH).get());
        return new ArchiveCommand(path);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
