package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static boolean isAscending = false;

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_SORT);

        boolean isInspect = AddressBookParser.getInspect();
        if (!arePrefixesPresent(argMultimap, PREFIX_SORT)
                || !argMultimap.getPreamble().isEmpty()) {
            String messageUsage;
            if (isInspect) {
                messageUsage = isAscending
                        ? SortCommand.MESSAGE_USAGE_ASCENDING_INSPECT
                        : SortCommand.MESSAGE_USAGE_DESCENDING_INSPECT;
            } else {
                messageUsage = isAscending
                        ? SortCommand.MESSAGE_USAGE_ASCENDING_MAIN
                        : SortCommand.MESSAGE_USAGE_DESCENDING_MAIN;

            }
             throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT);

        String attribute = isInspect
                         ? ParserUtil.parseDeliveryAttribute(argMultimap.getValue(PREFIX_SORT).get())
                         : ParserUtil.parseContactAttribute(argMultimap.getValue(PREFIX_SORT).get());

        return new SortCommand(attribute, isAscending);
    }

    public static void setAscending(boolean isAscending) {
        SortCommandParser.isAscending = isAscending;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
