package seedu.address.logic.parser;

import seedu.address.logic.commands.ListBackupsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListBackupsCommand object.
 */
public class ListBackupsCommandParser implements Parser<ListBackupsCommand> {

    @Override
    public ListBackupsCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException("The listbackups command does not accept any arguments.");
        }
        return new ListBackupsCommand();
    }
}
