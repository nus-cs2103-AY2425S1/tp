package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventNameMatchesKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewEventCommand object
 */
public class ViewEventCommandParser implements Parser<ViewEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewEventCommand
     * and returns a ViewEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewEventCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEventCommand.MESSAGE_USAGE));
        }

        return new ViewEventCommand(new EventNameMatchesKeywordsPredicate(trimmedArgs));
    }
}
