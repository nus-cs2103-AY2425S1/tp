package seedu.address.logic.parser;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ViewPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonFulfilsPredicate;
/**
 * Parses input arguments and creates a new ViewPersonCommand object
 */
public class ViewPersonCommandParser implements Parser<ViewPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewPersonCommand
     * and returns an ViewPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewPersonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String keyword;

        if (trimmedArgs.isEmpty()) {
            keyword = "";
        } else {
            keyword = trimmedArgs.split("\\s+")[0];
            if (!keyword.equals("buyer") && !keyword.equals("seller")) {
                throw new ParseException(
                        String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewPersonCommand.MESSAGE_USAGE)
                );
            }
        }

        return new ViewPersonCommand(new PersonFulfilsPredicate(keyword));
    }
}
