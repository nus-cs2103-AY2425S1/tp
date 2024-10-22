package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ASCENDING;

import seedu.address.logic.commands.SortDeliveryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DeliverySortCostComparator;

import java.util.stream.Stream;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT_ASCENDING);
        //  if (!arePrefixesPresent(argMultimap, PREFIX_SORT_ASCENDING) || !argMultimap.getPreamble().isEmpty()) {
        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_ASCENDING)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortDeliveryCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT_ASCENDING);
        SortOrder sortOrder = ParserUtil.parseSortOrder(argMultimap.getValue(PREFIX_SORT_ASCENDING).get());

        return new SortDeliveryCommand(new DeliverySortCostComparator(sortOrder));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
