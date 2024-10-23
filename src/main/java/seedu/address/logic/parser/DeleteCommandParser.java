package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

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
            Nric nric = ParserUtil.parseNric(args);
            return new DeleteCommand(nric);
        } catch (ParseException pe) {
            try {
                // If parsing as NRIC fails, try to parse it as an index
                Index index = ParserUtil.parseIndex(args.trim());
                return new DeleteCommand(index);
            } catch (ParseException indexParseException) {
                // If both NRIC and index parsing fail, throw an error with the appropriate message
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE),
                            indexParseException);
            }
        }
    }

}
