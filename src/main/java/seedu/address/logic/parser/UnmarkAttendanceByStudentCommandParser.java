package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAttendanceByStudentCommand;
import seedu.address.logic.commands.UnmarkAttendanceByStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

/**
 * Parses input arguments and creates a new UnmarkAttendanceByStudentCommand object.
 */
public class UnmarkAttendanceByStudentCommandParser implements Parser<UnmarkAttendanceByStudentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkAttendanceByStudentCommand
     * and returns a UnmarkAttendanceByStudentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnmarkAttendanceByStudentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ATTENDANCE, PREFIX_TUTORIAL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE), e);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ATTENDANCE, PREFIX_TUTORIAL);

        if (argMultimap.getValue(PREFIX_ATTENDANCE).isEmpty() || argMultimap.getValue(PREFIX_TUTORIAL).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByStudentCommand.MESSAGE_USAGE));
        }

        Attendance attendance = ParserUtil.parseAttendance(argMultimap.getValue(PREFIX_ATTENDANCE).get());
        Tutorial tutorial = ParserUtil.parseTutorial(argMultimap.getValue(PREFIX_TUTORIAL).get());

        return new UnmarkAttendanceByStudentCommand(index, attendance, tutorial);
    }
}
