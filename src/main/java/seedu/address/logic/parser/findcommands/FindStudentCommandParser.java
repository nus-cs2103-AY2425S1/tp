package seedu.address.logic.parser.findcommands;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.findcommands.FindStudentCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.StudentMatchesQueryPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindStudentCommandParser implements Parser<FindStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindStudentCommand
     * and returns a FindStudentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindStudentCommand parse(String args) throws ParseException {
        String strippedArgs = args.strip().toLowerCase();
        if (strippedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindStudentCommand.MESSAGE_USAGE));
        }
        return new FindStudentCommand(new StudentMatchesQueryPredicate(strippedArgs));
    }
}
