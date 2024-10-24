package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALID;

import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import seedu.address.logic.commands.UnattendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;

/**
 * Parses input arguments and creates a new AttendCommand object.
 */
public class UnattendCommandParser implements Parser<UnattendCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AttendCommand
     * and returns an AttendCommand object for execution.
     * @param args The input arguments string to parse.
     * @return AttendCommand object based on parsed arguments.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public UnattendCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENTID, PREFIX_TUTORIALID, PREFIX_ATTENDANCEDATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENTID, PREFIX_TUTORIALID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnattendCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENTID, PREFIX_TUTORIALID, PREFIX_ATTENDANCEDATE);
        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENTID).get());
        TutorialId tutorialId = ParserUtil.parseTutorialId(argMultimap.getValue(PREFIX_TUTORIALID).get());
        Date tutDate = argMultimap.getValue(PREFIX_ATTENDANCEDATE).isPresent()
                ? ParserUtil.parseDate(argMultimap.getValue(PREFIX_ATTENDANCEDATE).get()) : getTodayDateWithoutTime();
        return new UnattendCommand(studentId, tutorialId, tutDate);
    }

    private static Date getTodayDateWithoutTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
