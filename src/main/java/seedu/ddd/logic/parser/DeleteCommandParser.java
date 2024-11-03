package seedu.ddd.logic.parser;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.logic.commands.DeleteCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        Index index = ParserUtil.parseIndex(args);
        return new DeleteCommand(index);
    }
}
