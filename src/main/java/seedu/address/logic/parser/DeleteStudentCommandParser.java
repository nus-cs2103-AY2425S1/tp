package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteStudentCommand object
 */
public class DeleteStudentCommandParser implements Parser<DeleteStudentCommand> {
    @Override
    public DeleteStudentCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE));
        }

        Name nameToDelete;

        try {
            nameToDelete = ParserUtil.parseName(trimmedArgs);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteStudentCommand(nameToDelete);
    }
}
