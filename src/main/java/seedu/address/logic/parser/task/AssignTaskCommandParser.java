package seedu.address.logic.parser.task;

import seedu.address.logic.commands.task.AssignTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignTaskCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AssignTaskCommand
     * and returns an AssignTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignTaskCommand parse(String args) throws ParseException {
        TaskAssignmentParserUtil.ParsedCommandData parsedData =
                TaskAssignmentParserUtil.parseTaskCommand(args, AssignTaskCommand.MESSAGE_USAGE);

        return new AssignTaskCommand(parsedData.personIndex, parsedData.taskIndexes);
    }
}
