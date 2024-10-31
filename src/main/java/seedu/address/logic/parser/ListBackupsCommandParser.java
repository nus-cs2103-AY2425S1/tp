package seedu.address.logic.parser;

import seedu.address.logic.commands.ListBackupsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments to create a new ListBackupsCommand object.
 * Ensures that no arguments are provided, as the command does not require them.
 */
public class ListBackupsCommandParser implements Parser<ListBackupsCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a ListBackupsCommand object for execution.
     * Validates that no additional arguments are provided; otherwise, a ParseException is thrown.
     *
     * @param args The user input arguments.
     * @return A ListBackupsCommand object.
     * @throws ParseException If arguments are provided, as this command does not accept any.
     */
    @Override
    public ListBackupsCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException("The listbackups command does not accept any arguments.");
        }
        return new ListBackupsCommand();
    }
}
