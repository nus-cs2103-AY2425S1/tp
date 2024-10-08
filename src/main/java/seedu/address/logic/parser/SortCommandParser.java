package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] tokens = args.trim().split("\\s+");

        if (tokens.length < 1 || tokens.length > 2) {
            throw new ParseException(SortCommand.MESSAGE_USAGE);
        }

        String parameter = tokens[0];
        String order = tokens.length == 2 ? tokens[1] : "asc";

        return new SortCommand(parameter, order);
    }
}
