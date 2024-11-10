package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
        if (args.trim().isEmpty()) {
            throw new ParseException("Please enter a valid index.");
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

}
