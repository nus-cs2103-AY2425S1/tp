package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.list_commands.ListProjectMembersCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.AssignmentProjectPredicate;

/**
 * Parses input arguments and creates a new ListProjectMembersCommand object.
 */
public class ListProjectMembersCommandParser implements Parser<ListProjectMembersCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListProjectMembersCommand
     * and returns a ListProjectMembersCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ListProjectMembersCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListProjectMembersCommand.MESSAGE_USAGE));
        }

        // Create the predicate to filter assignments by the project name
        AssignmentProjectPredicate predicate = new AssignmentProjectPredicate(trimmedArgs);

        return new ListProjectMembersCommand(predicate);
    }
}
