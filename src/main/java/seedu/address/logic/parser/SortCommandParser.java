package seedu.address.logic.parser;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

public class SortCommandParser implements Parser<SortCommand> {
    private static boolean isAscending = false;

    public SortCommand parse(String args) throws ParseException {
        if (AddressBookParser.getInspect()) {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_SORT);

            if (!arePrefixesPresent(argMultimap, PREFIX_SORT)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        (isAscending ? SortCommand.MESSAGE_USAGE_ASCENDING : SortCommand.MESSAGE_USAGE_DESCENDING)));
            }
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT);

            String attribute = ParserUtil.parseAttribute(argMultimap.getValue(PREFIX_SORT).get());
            return new SortCommand(attribute, isAscending);
        } else {
            // Dummy string to trigger MESSAGE_FAILURE error in CommandResult instead of parsing error.
            return new SortCommand("", isAscending);
        }
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
