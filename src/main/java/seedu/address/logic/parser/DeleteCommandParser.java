package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_NAME_DISPLAYED;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Name name = ParserUtil.parseName(args);
            return new DeleteCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_NAME_DISPLAYED, pe);
        }
    }

}
