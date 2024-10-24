package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String PRIORITY = "priority";
    public static final String INCOME = "income";

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input is not a valid field to sort the person list by.
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedParameter = args.trim().toLowerCase();

        switch (trimmedParameter) {

        case NAME:
            return new SortCommand(NAME);

        case ADDRESS:
            return new SortCommand(ADDRESS);

        case PRIORITY:
            return new SortCommand(PRIORITY);

        case INCOME:
            return new SortCommand(INCOME);

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
