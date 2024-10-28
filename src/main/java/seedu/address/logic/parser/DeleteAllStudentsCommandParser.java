package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteAllStudentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAllStudentsCommand object
 */
public class DeleteAllStudentsCommandParser implements Parser<DeleteAllStudentsCommand> {
    @Override
    public DeleteAllStudentsCommand parse(String args) throws ParseException {
        String trimmedInput = args.trim();
        if (!trimmedInput.isEmpty()) {
            throw new ParseException(DeleteAllStudentsCommand.MESSAGE_USAGE);
        }
        return new DeleteAllStudentsCommand();
    }
}
