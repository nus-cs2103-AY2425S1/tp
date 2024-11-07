package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.DeleteSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.supplier.Name;

/**
 * Parses input arguments and creates a new DeleteSupplierCommand object.
 */
public class DeleteSupplierCommandParser implements Parser<DeleteSupplierCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSupplierCommand
     * and returns a DeleteSupplierCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteSupplierCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        ParserUtil.verifyInput(argMultimap, new Prefix[]{PREFIX_NAME},
                DeleteSupplierCommand.MESSAGE_USAGE);
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME);
        Name supplierName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        return new DeleteSupplierCommand(supplierName);
    }
}
