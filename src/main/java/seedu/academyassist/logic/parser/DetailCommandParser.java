package seedu.academyassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.academyassist.logic.commands.DetailCommand;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.person.StudentId;

/**
 * Parse input arguments and creates a new DetailCommand object
 */
public class DetailCommandParser implements Parser<DetailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DetailCommand
     * and returns an DetailCommand object for execution.
     *
     * @param args the String to be parsed
     * @return an DetailCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public DetailCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            StudentId studentId = ParserUtil.parseStudentId(args);
            return new DetailCommand(studentId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailCommand.MESSAGE_USAGE, pe));
        }
    }
}
