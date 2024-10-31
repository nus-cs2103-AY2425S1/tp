package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FilterClientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Name;


/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class FilterClientCommandParser implements Parser<FilterClientCommand> {
    private static final Logger logger = LogsCenter.getLogger(FilterClientCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterClientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);
        if (hasExcessToken(args, PREFIX_NAME)) {
            logger.warning("Excess prefixes.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterClientCommand.MESSAGE_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterClientCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_NAME).get());
        return new FilterClientCommand(name);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The argument multimap that holds the parsed arguments.
     * @param prefixes The prefixes to check for presence.
     * @return True if all prefixes contain non-empty values, false otherwise.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if number of tokens in args string exceeds specified prefixes.
     */
    private boolean hasExcessToken(String args, Prefix... prefixes) {
        String[] splits = args.trim().split("\\s(?=\\S+/)");
        if (splits[0].equals("/")) {
            return false;
        }
        return splits.length > prefixes.length;
    }
}
