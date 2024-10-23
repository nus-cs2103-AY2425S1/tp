package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import seedu.address.logic.commands.MarkAttendanceByTutorialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new MarkAttendanceByTutorialCommand object
 */
public class MarkAttendanceByTutorialCommandParser implements Parser<MarkAttendanceByTutorialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceByTutorialCommand
     * and returns a MarkAttendanceByTutorialCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAttendanceByTutorialCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ATTENDANCE, PREFIX_TUTORIAL);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ATTENDANCE, PREFIX_TUTORIAL);

        if (argMultimap.getValue(PREFIX_ATTENDANCE).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_TUTORIAL).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceByTutorialCommand.MESSAGE_USAGE));
        }

        String attendance = argMultimap.getValue(PREFIX_ATTENDANCE).get();
        if (!Attendance.isValidAttendance(attendance)) {
            throw new ParseException(Attendance.MESSAGE_CONSTRAINTS);
        }

        Tutorial tutorial = ParserUtil.parseTutorial(argMultimap.getValue(PREFIX_TUTORIAL).get());

        return new MarkAttendanceByTutorialCommand(tutorial, attendance);
    }
}
