package seedu.address.logic.parser.task;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.task.UnmarkTaskCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnmarkTaskCommand object.
 */
public class UnmarkTaskCommandParser implements Parser<UnmarkTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkTaskCommand
     * and returns an UnmarkTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UnmarkTaskCommand parse(String args) throws ParseException {
        String[] splitArgs = args.trim().split("\\s+");
        if (args == null || args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkTaskCommand.MESSAGE_USAGE));
        }
        Set<Index> targetIndexes = TaskAssignmentParserUtil.parseMultipleIndexes(splitArgs, 0);
        return new UnmarkTaskCommand(targetIndexes);
    }
}
