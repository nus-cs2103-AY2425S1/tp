package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAttendanceByStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new MarkAttendanceByStudentCommand object
 */
public class MarkAttendanceByStudentCommandParser implements Parser<MarkAttendanceByStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceByStudentCommand
     * and returns a MarkAttendanceByStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAttendanceByStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ATTENDANCE, PREFIX_TUTORIAL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ATTENDANCE, PREFIX_TUTORIAL);

        if (argMultimap.getValue(PREFIX_ATTENDANCE).isEmpty() || argMultimap.getValue(PREFIX_TUTORIAL).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));
        }

        Attendance attendance = ParserUtil.parseAttendance(argMultimap.getValue(PREFIX_ATTENDANCE).get());
        Tutorial tutorial = ParserUtil.parseTutorial(argMultimap.getValue(PREFIX_TUTORIAL).get());

        return new MarkAttendanceByStudentCommand(index, attendance, tutorial);
    }
}
