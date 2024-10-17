package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the {@code DeletePriorityCommand}.
 * <p>
 * This class is responsible for interpreting the user's input when they attempt to delete the priority
 * level of a person in the address book. It validates the input format and extracts the necessary
 * information to create a {@code DeletePriorityCommand}.
 * </p>
 */
public class DeletePriorityCommandParser implements Parser<DeletePriorityCommand> {

    /**
     * Parses the given arguments and creates a new {@code DeletePriorityCommand} object.
     * <p>
     * This method processes the input string, extracting the index of the person whose priority level
     * is to be deleted. The index is parsed and validated to ensure it is a valid positive integer.
     * </p>
     *
     * @param args The input arguments provided by the user, typically containing the index of the person.
     * @return A {@code DeletePriorityCommand} object containing the parsed index.
     * @throws ParseException If the input is invalid or the index cannot be parsed. This may include scenarios
     *     where the index is not a valid positive integer or if the input format is incorrect.
     */
    @Override
    public DeletePriorityCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args.trim());
            return new DeletePriorityCommand(index.getOneBased());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePriorityCommand.MESSAGE_USAGE), pe);
        }
    }
}
