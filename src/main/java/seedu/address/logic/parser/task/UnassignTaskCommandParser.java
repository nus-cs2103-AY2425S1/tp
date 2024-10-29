package seedu.address.logic.parser.task;

import seedu.address.logic.commands.task.UnassignTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnassignTaskCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the UnassignTaskCommand
     * and returns an UnassignTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnassignTaskCommand parse(String args) throws ParseException {
        TaskAssignmentParserUtil.ParsedCommandData parsedData =
                TaskAssignmentParserUtil.parseTaskCommand(args, UnassignTaskCommand.MESSAGE_USAGE);

        return new UnassignTaskCommand(parsedData.personIndex, parsedData.taskIndexes);
    }
}
