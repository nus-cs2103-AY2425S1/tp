package seedu.address.logic.parser.buyer;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.buyer.ViewCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.buyer.BuyerFulfilsPredicate;
/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String keyword;

        if (trimmedArgs.isEmpty()) {
            keyword = "";
        } else {
            keyword = trimmedArgs.split("\\s+")[0];
            if (!keyword.equals("buyer") && !keyword.equals("seller")) {
                throw new ParseException(
                        String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE)
                );
            }
        }

        return new ViewCommand(new BuyerFulfilsPredicate(keyword));
    }
}
