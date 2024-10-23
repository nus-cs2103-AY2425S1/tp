package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ViewProductCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.product.ProductNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewSupplierCommand object
 */
public class ViewProductCommandParser implements Parser<ViewProductCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewSupplierCommand
     * and returns a ViewSupplierCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewProductCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewProductCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new ViewProductCommand(new ProductNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
