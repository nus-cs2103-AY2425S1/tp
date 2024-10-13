package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;



/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteAssignmentCommandParser implements Parser<DeleteAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAssignmentCommand parse(String args) throws ParseException {

        try {
            String trimmed = args.trim();

            if (trimmed.isEmpty() || trimmed.isBlank()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE)
                );
            }

            LocalDateTime defaultDate = ParserUtil.parseDueDate("2025-12-12 1200");
            Assignment assignment = new Assignment(trimmed, defaultDate);
            return new DeleteAssignmentCommand(assignment);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE), pe
            );
        }
    }

}
