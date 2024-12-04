package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_NAME;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME);
        ParserUtil.verifyInput(argMultimap, new Prefix[]{PREFIX_PRODUCT_NAME, PREFIX_SUPPLIER_NAME},
                AssignProductCommand.MESSAGE_USAGE);
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_PRODUCT_NAME,
                PREFIX_SUPPLIER_NAME);

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_PRODUCT_NAME,
                PREFIX_SUPPLIER_NAME);
        String productNameStr = argMultimap.getValue(PREFIX_PRODUCT_NAME).get();
        String supplierNameStr = argMultimap.getValue(PREFIX_SUPPLIER_NAME).get();

        ProductName productName = ParserUtil.parseProductName(productNameStr);
        Name supplierName = ParserUtil.parseName(supplierNameStr);

        return new AssignProductCommand(productName, supplierName);
    }

}
