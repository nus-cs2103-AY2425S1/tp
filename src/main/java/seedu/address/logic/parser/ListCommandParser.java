package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonComparators;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        switch (trimmedArgs.toLowerCase()) {
        case "":
            // Default sort order is timeAdded asc
            return new ListCommand(PersonComparators.BY_ORDER_ADDED_REVERSED);
        case "timeadded", "timeadded asc":
            return new ListCommand(PersonComparators.BY_ORDER_ADDED_REVERSED);
        case "timeadded desc":
            return new ListCommand(PersonComparators.BY_ORDER_ADDED);
        case "name", "name asc":
            return new ListCommand(PersonComparators.BY_NAME);
        case "name desc":
            return new ListCommand(PersonComparators.BY_NAME_REVERSED);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}
