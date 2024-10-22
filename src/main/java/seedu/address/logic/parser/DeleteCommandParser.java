package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        String[] splitIndices = args.split(" ");
        String personIndexArg = splitIndices[1];
        String emergencyContactIndexArg = splitIndices.length > 2 ? splitIndices[2] :
                ParserUtil.NO_EMERGENCY_CONTACT_INDEX;
        try {
            Index personIndex = ParserUtil.parseIndex(personIndexArg);
            Index emergencyContactIndex = ParserUtil.parseIndex(emergencyContactIndexArg);
            return new DeleteCommand(personIndex, emergencyContactIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
