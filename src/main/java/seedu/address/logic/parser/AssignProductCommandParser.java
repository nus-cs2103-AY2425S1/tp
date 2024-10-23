package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AssignProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;

/**
 * Parses input arguments and creates a new AssignProductCommand object
 */
public class AssignProductCommandParser implements Parser<AssignProductCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AssignProductCommand
     * and returns a AssignProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignProductCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignProductCommand.MESSAGE_USAGE));
        }

        ProductName productName = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());
        Name supplierName = ParserUtil.parseName(argMultimap.getValue(PREFIX_SUPPLIER_NAME).get());
        return new AssignProductCommand(productName, supplierName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
