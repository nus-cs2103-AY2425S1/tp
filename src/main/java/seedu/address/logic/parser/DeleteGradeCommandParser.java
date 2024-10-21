package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteGradeCommand object.
 */
public class DeleteGradeCommandParser implements Parser<DeleteGradeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGradeCommand
     * and returns a DeleteGradeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteGradeCommand parse(String args) throws ParseException {
        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGradeCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(splitArgs[0]);
        String testName = ParserUtil.parseTestName(splitArgs[1]);

        return new DeleteGradeCommand(index, testName);
    }
}
