package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.Pair;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteOwnerCommand;
import seedu.address.logic.commands.DeletePetCommand;
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
        try {
            Pair p = ParserUtil.parseIndexAndType(args);
            if (p.second().equals("p")) {
                return new DeletePetCommand(p.first());
            } else if (p.second().equals("o")) {
                return new DeleteOwnerCommand(p.first());
            } else {
                throw new ParseException("Invalid type to delete");
            }
        } catch (ParseException pe) {
            if (pe.getMessage() == "Index is not a non-zero unsigned integer.") {
                throw pe;
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
