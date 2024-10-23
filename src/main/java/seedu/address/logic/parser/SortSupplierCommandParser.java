package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SupplierSortBy;
import seedu.address.model.person.SupplierSortComparator;

/**
 * Parses input arguments and creates a new SortSupplierCommand object.
 */
public class SortSupplierCommandParser implements Parser<SortSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortSupplierCommand
     * and returns a SortSupplierCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public SortSupplierCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT_ORDER, PREFIX_SORT_BY);
        if (!arePrefixesPresent(argMultimap, PREFIX_SORT_ORDER, PREFIX_SORT_BY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortSupplierCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT_ORDER, PREFIX_SORT_BY);
        SortOrder sortOrder = ParserUtil.parseSortOrder(argMultimap.getValue(PREFIX_SORT_ORDER).get());
        SupplierSortBy sortBy = ParserUtil.parseSupplierSortBy(argMultimap.getValue(PREFIX_SORT_BY).get());
        SupplierSortComparator supplierSortComparator = SupplierSortBy.getSupplierSortComparator(sortOrder, sortBy);
        return new SortSupplierCommand(supplierSortComparator);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
