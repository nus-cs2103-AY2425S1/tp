package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.sellsavvy.logic.commands.customercommands.FindCustomerCommand;
import seedu.sellsavvy.logic.parser.exceptions.ParseException;
import seedu.sellsavvy.model.customer.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCustomerCommand object.
 */
public class FindCustomerCommandParser implements Parser<FindCustomerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCustomerCommand
     * and returns a FindCustomerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCustomerCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCustomerCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCustomerCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
