package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.sellsavvy.commons.exceptions.IllegalValueException;
import seedu.sellsavvy.logic.commands.ordercommands.FilterOrderCommand;
import seedu.sellsavvy.logic.parser.exceptions.ParseException;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.model.order.StatusEqualsKeywordPredicate;

/**
 * Parses input arguments and creates a new FilterOrderCommand object.
 */
public class FilterOrderCommandParser implements Parser<FilterOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterOrderCommand
     * and returns a FilterOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterOrderCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        try {
            StatusEqualsKeywordPredicate predicate = new StatusEqualsKeywordPredicate(Status.fromString(trimmedArgs));
            return new FilterOrderCommand(predicate);
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterOrderCommand.MESSAGE_USAGE));
        }
    }

}
