package seedu.address.logic.parser;

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
        String[] splitArgs = args.trim().split("\\s+", 2);

        if (splitArgs.length != 2) {
            throw new ParseException(String.format("Invalid command format! \n%1$s", DeleteGradeCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(splitArgs[0]);
        String testName = splitArgs[1].trim();

        return new DeleteGradeCommand(index, testName);
    }
}
