package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SortIndividualCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortIndividualCommand object
 */
public class SortIndividualCommandParser implements Parser<SortIndividualCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortIndividualCommand
     * and returns an SortIndividualCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortIndividualCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FIELD, PREFIX_ORDER);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortIndividualCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_FIELD, PREFIX_ORDER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortIndividualCommand.MESSAGE_USAGE));
        }
        if (!isValidFieldArgument(argMultimap.getValue(PREFIX_FIELD).get())) {
            throw new ParseException(SortIndividualCommand.MESSAGE_AVAILABLE_FIELDS);
        }

        if (!isValidOrderArgument(argMultimap.getValue(PREFIX_ORDER).get())) {
            throw new ParseException(SortIndividualCommand.MESSAGE_INVALID_ORDER);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ORDER);

        return new SortIndividualCommand(index, argMultimap.getValue(PREFIX_FIELD).get(),
                argMultimap.getValue(PREFIX_ORDER).get());
    }
    /**
     * Returns true if the prefixes contain all the required fields.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    /**
     * Returns true if the order argument is valid.
     */
    private static boolean isValidOrderArgument(String order) {
        return (order.equals("H") || order.equals("L"));
    }

    /**
     * Returns true if the field argument is valid.
     */
    private static boolean isValidFieldArgument(String field) {
        return field.equals("Price");
    }
}
