package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;

import seedu.address.logic.commands.UnassignProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;

/**
 * Parses input arguments and creates a new UnassignProductCommand object
 */
public class UnassignProductCommandParser implements Parser<UnassignProductCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnassignProductCommand
     * and returns an UnassignProductCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UnassignProductCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME);
        ParserUtil.verifyInput(argMultimap, new Prefix[]{PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME},
                UnassignProductCommand.MESSAGE_USAGE);
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_PRODUCT_NAME,
                PREFIX_SUPPLIER_NAME);
        ProductName productName = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());
        Name supplierName = ParserUtil.parseName(argMultimap.getValue(PREFIX_SUPPLIER_NAME).get());

        return new UnassignProductCommand(productName, supplierName);
    }

}
