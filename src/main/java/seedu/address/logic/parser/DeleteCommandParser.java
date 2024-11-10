package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }


        try {
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            if (trimmedArgs.matches("[^\\d]*")) { // Checks if the input does not contain digits
                try {
                    Name name = ParserUtil.parseName(trimmedArgs);
                    return new DeleteCommand(name);
                } catch (ParseException pe2) {
                    // If name parsing fails, throw the specific name-related error message
                    throw new ParseException(pe2.getMessage(), pe2);
                }
            } else {
                // If it's not a name and not a valid index, throw an invalid index message
                throw new ParseException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }
}
