package careconnect.logic.parser;

import static careconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import careconnect.commons.core.index.Index;
import careconnect.logic.commands.DeleteLogCommand;
import careconnect.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteLogCommandParser implements Parser<DeleteLogCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLogCommand
     * and returns a DeleteLogCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLogCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_LOG_INDEX);

        Index personIndex;
        Index logIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLogCommand.MESSAGE_USAGE),
                    pe);
        }

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_LOG_INDEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLogCommand.MESSAGE_USAGE));
        }
        logIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_LOG_INDEX).get());
        return new DeleteLogCommand(personIndex, logIndex);
    }

}
