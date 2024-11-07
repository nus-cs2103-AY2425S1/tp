package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.DeleteProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductName;

/**
 * Parses input arguments and creates a new DeleteProductCommand object.
 */
public class DeleteProductCommandParser implements Parser<DeleteProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProductCommand
     * and returns a DeleteProductCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteProductCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        ParserUtil.verifyInput(argMultimap, new Prefix[]{PREFIX_NAME},
                DeleteProductCommand.MESSAGE_USAGE);
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME);
        ProductName productName = ParserUtil.parseProductName(argMultimap.getValue(PREFIX_NAME).get());

        return new DeleteProductCommand(productName);
    }

}
