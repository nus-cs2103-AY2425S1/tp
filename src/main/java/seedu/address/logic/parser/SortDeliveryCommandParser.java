package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortDeliveryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DeliverySortBy;
import seedu.address.model.delivery.DeliverySortComparator;

/**
 * Parses input arguments and creates a new SortDeliveryCommand object.
 */
public class SortDeliveryCommandParser implements Parser<SortDeliveryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortDeliveryCommand
     * and returns a SortDeliveryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SortDeliveryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT_ORDER, PREFIX_SORT_BY);
        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_ORDER, PREFIX_SORT_BY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortDeliveryCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT_ORDER, PREFIX_SORT_BY);
        SortOrder sortOrder = ParserUtil.parseSortOrder(argMultimap.getValue(PREFIX_SORT_ORDER).get());
        DeliverySortBy sortBy = ParserUtil.parseDeliverySortBy(argMultimap.getValue(PREFIX_SORT_BY).get());
        DeliverySortComparator deliverySortComparator = DeliverySortBy.getDeliverySortComparator(sortOrder, sortBy);
        return new SortDeliveryCommand(deliverySortComparator);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
