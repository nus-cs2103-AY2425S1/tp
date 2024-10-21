package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListEventsCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ListEventsCommand object.
 */
public class ListEventsCommandParser implements Parser<ListEventsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListEventsCommand
     * and returns a ListEventsCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public ListEventsCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();

        if (!trimmedInput.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListEventsCommand.MESSAGE_USAGE));
        }

        return new ListEventsCommand();
    }
}
